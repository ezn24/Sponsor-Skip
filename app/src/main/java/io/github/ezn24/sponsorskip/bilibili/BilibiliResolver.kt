/*
 * Sponsor Skip - Auto-skips SponsorBlock segments in YouTube videos
 * Copyright (C) 2026 Jaival
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.ezn24.sponsorskip.bilibili

import android.media.MediaMetadata
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder
import kotlin.math.abs

data class BilibiliVideoId(val bvid: String, val cid: String? = null)

class BilibiliResolver(private val client: OkHttpClient) {
    private val resolutionCache = object : LinkedHashMap<String, BilibiliVideoId>(16, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, BilibiliVideoId>?): Boolean = size > 20
    }

    fun resolve(metadata: MediaMetadata?, title: String, durationMs: Long): BilibiliVideoId? {
        val cacheKey = "${normalizeTitle(title)}|${durationMs / 1000L}"
        synchronized(resolutionCache) { resolutionCache[cacheKey] }?.let {
            AppLogger.log("[BILI RESOLVER] Reused cached BVID=${it.bvid}, CID=${it.cid ?: "unknown"}")
            return it
        }

        extractBvid(metadata)?.let { bvid ->
            AppLogger.log("[BILI RESOLVER] BVID found directly in media metadata: $bvid")
            return cache(cacheKey, BilibiliVideoId(bvid))
        }

        extractBvidFromNumericMediaId(metadata)?.let { bvid ->
            AppLogger.log("[BILI RESOLVER] Converted numeric media ID to BVID: $bvid")
            return cache(cacheKey, BilibiliVideoId(bvid))
        }

        val query = URLEncoder.encode(title, "UTF-8")
        val url = "https://api.bilibili.com/x/web-interface/search/type?search_type=video&keyword=$query"
        AppLogger.log("[BILI RESOLVER] Searching bilibili by media title: '$title'")
        val response = client.newCall(
            Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .header("Referer", "https://search.bilibili.com/")
                .build()
        ).execute()

        response.use {
            AppLogger.log("[BILI RESOLVER] Search response code: ${it.code}")
            if (!it.isSuccessful) return null
            val root = JSONObject(it.body?.string().orEmpty())
            if (root.optInt("code", -1) != 0) {
                AppLogger.log("[BILI RESOLVER] Search API error: ${root.optString("message")}")
                return null
            }

            val results = root.optJSONObject("data")?.optJSONArray("result") ?: return null
            val normalizedTarget = normalizeTitle(title)
            val durationSec = durationMs.takeIf { value -> value > 0L }?.div(1000L)
            var bestBvid: String? = null
            var bestScore = Int.MIN_VALUE

            for (index in 0 until results.length()) {
                val item = results.optJSONObject(index) ?: continue
                val bvid = item.optString("bvid")
                if (!BVID_REGEX.matches(bvid)) continue
                val candidateTitle = normalizeTitle(item.optString("title"))
                val candidateDuration = parseDuration(item.optString("duration"))
                val score = titleScore(normalizedTarget, candidateTitle) + durationScore(durationSec, candidateDuration)
                AppLogger.log("[BILI RESOLVER] Candidate $bvid score=$score duration=${candidateDuration ?: -1}s")
                if (score > bestScore) {
                    bestScore = score
                    bestBvid = bvid
                }
            }

            if (bestBvid == null || bestScore < MIN_ACCEPTED_SCORE) {
                AppLogger.log("[BILI RESOLVER] No reliable BVID match (best score=$bestScore)")
                return null
            }

            AppLogger.log("[BILI RESOLVER] Selected BVID $bestBvid (score=$bestScore)")
            return cache(cacheKey, BilibiliVideoId(bestBvid))
        }
    }

    private fun cache(key: String, value: BilibiliVideoId): BilibiliVideoId {
        synchronized(resolutionCache) { resolutionCache[key] = value }
        return value
    }

    private fun extractBvid(metadata: MediaMetadata?): String? {
        metadata ?: return null
        for (key in metadata.keySet()) {
            val text = try {
                metadata.getString(key)
            } catch (e: Exception) {
                AppLogger.log("[BILI RESOLVER] Could not read metadata key '$key': ${e.message}")
                null
            }
            BVID_REGEX.find(text.orEmpty())?.value?.let { return it }
        }
        return null
    }

    private fun extractBvidFromNumericMediaId(metadata: MediaMetadata?): String? {
        val rawMediaId = metadata?.getString(MediaMetadata.METADATA_KEY_MEDIA_ID) ?: return null
        val encodedId = rawMediaId.toLongOrNull()?.takeIf { it > 0L } ?: return null
        val avid = encodedId / 10L
        if (avid <= 0L || avid >= MAX_AVID) return null

        val chars = charArrayOf('B', 'V', '1', '0', '0', '0', '0', '0', '0', '0', '0', '0')
        var index = chars.lastIndex
        var value = (MAX_AVID or avid) xor XOR_CODE
        while (value > 0L && index >= 0) {
            chars[index] = BVID_ALPHABET[(value % BVID_BASE).toInt()]
            value /= BVID_BASE
            index--
        }
        chars.swap(3, 9)
        chars.swap(4, 7)
        return String(chars).takeIf(BVID_REGEX::matches)
    }

    private fun CharArray.swap(first: Int, second: Int) {
        val temporary = this[first]
        this[first] = this[second]
        this[second] = temporary
    }

    fun resolveCid(bvid: String, title: String, durationMs: Long): String? {
        val url = "https://api.bilibili.com/x/web-interface/view?bvid=$bvid"
        val response = client.newCall(
            Request.Builder().url(url).header("User-Agent", USER_AGENT).header("Referer", "https://www.bilibili.com/").build()
        ).execute()

        response.use {
            AppLogger.log("[BILI RESOLVER] View response code for $bvid: ${it.code}")
            if (!it.isSuccessful) return null
            val root = JSONObject(it.body?.string().orEmpty())
            val pages = root.optJSONObject("data")?.optJSONArray("pages") ?: return null
            if (pages.length() == 1) return pages.optJSONObject(0)?.optString("cid")?.takeIf(String::isNotBlank)

            val targetDuration = durationMs.takeIf { value -> value > 0L }?.div(1000L)
            val normalizedTitle = normalizeTitle(title)
            var bestCid: String? = null
            var bestScore = Int.MIN_VALUE
            for (index in 0 until pages.length()) {
                val page = pages.optJSONObject(index) ?: continue
                val cid = page.optString("cid")
                if (cid.isBlank()) continue
                val pageTitle = normalizeTitle(page.optString("part"))
                val pageDuration = page.optLong("duration", -1L).takeIf { value -> value >= 0L }
                val score = titleScore(normalizedTitle, pageTitle) + durationScore(targetDuration, pageDuration)
                if (score > bestScore) {
                    bestScore = score
                    bestCid = cid
                }
            }
            AppLogger.log("[BILI RESOLVER] Selected CID ${bestCid ?: "none"} from ${pages.length()} page(s), score=$bestScore")
            return bestCid
        }
    }

    private fun titleScore(target: String, candidate: String): Int {
        if (target.isBlank() || candidate.isBlank()) return 0
        if (target == candidate) return 100
        if (target.contains(candidate) || candidate.contains(target)) return 70
        val shared = target.toSet().intersect(candidate.toSet()).size
        val total = target.toSet().union(candidate.toSet()).size.coerceAtLeast(1)
        return shared * 50 / total
    }

    private fun durationScore(target: Long?, candidate: Long?): Int {
        if (target == null || candidate == null) return 0
        return when (abs(target - candidate)) {
            in 0..2 -> 40
            in 3..5 -> 30
            in 6..15 -> 10
            else -> -20
        }
    }

    private fun normalizeTitle(value: String): String = value
        .replace(HTML_TAG_REGEX, "")
        .replace(HTML_ENTITY_REGEX, "")
        .lowercase()
        .filter { it.isLetterOrDigit() }

    private fun parseDuration(value: String): Long? {
        val parts = value.split(':').mapNotNull(String::toLongOrNull)
        if (parts.isEmpty() || parts.size > 3) return null
        return parts.fold(0L) { total, part -> total * 60L + part }
    }

    companion object {
        private const val USER_AGENT = "Mozilla/5.0 (Linux; Android 14) SponsorSkipBilibili/1.0"
        private const val MIN_ACCEPTED_SCORE = 30
        private const val XOR_CODE = 23442827791579L
        private const val MAX_AVID = 1L shl 51
        private const val BVID_BASE = 58L
        private const val BVID_ALPHABET = "FcwAPNKTMug3GV5Lj7EJnHpWsx4tb8haYeviqBz6rkCy12mUSDQX9RdoZf"
        private val BVID_REGEX = Regex("BV[0-9A-Za-z]{10}")
        private val HTML_TAG_REGEX = Regex("<[^>]+>")
        private val HTML_ENTITY_REGEX = Regex("&[A-Za-z0-9#]+;")
    }
}
