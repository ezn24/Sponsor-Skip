<div align="center">

<img src="https://raw.githubusercontent.com/ezn24/Sponsor-Skip/refs/heads/bilibili/assets/graphics/icon.png" alt="Sponsor Skip for Bilibili 應用程式圖示" width="200" />

# Sponsor Skip for Bilibili

### 基於 [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock) 與 [Sponsor-Skip](https://github.com/jaival-11/Sponsor-Skip) 開發的 Android 原生廣告／贊助片段跳過工具

<br/>

[![Latest release](https://img.shields.io/github/v/release/ezn24/Sponsor-Skip?style=for-the-badge\&labelColor=0d1117)](https://github.com/ezn24/Sponsor-Skip/releases/latest)
[![License](https://img.shields.io/badge/License-GPL--3.0-blue?style=for-the-badge\&labelColor=0d1117\&color=EA7233)](https://github.com/ezn24/Sponsor-Skip/blob/bilibili/LICENSE)

<br/>

[**下載**](#下載) · [**功能**](#功能) · [**問題回報**](#問題回報與功能建議)

</div>

---

## 目錄

  * **[功能](#功能)**: Sponsor Skip for Bilibili 可以做什麼
    
  * **[運作方式](#運作方式)**: Sponsor Skip for Bilibili 的基本工作流程
    
  * **[下載](#下載)**: 取得最新版本
    
  * **[從原始碼建置](#從原始碼建置)**: 建置環境與編譯方式
    
  * **[貢獻](#貢獻)**: 如何參與專案開發
    
  * **[問題回報與功能建議](#問題回報與功能建議)**: 回報錯誤或提出新功能
    
  * **[技術資訊](#技術資訊)**: 使用技術與核心架構
    
  * **[致謝](#致謝)**: BilibiliSponsorBlock、SponsorBlock 與其他開源專案
    
  * **[隱私權](#隱私權)**: 隱私權政策
    
  * **[免責聲明](#免責聲明)**: 第三方關係、責任與使用條款
    
  * **[授權條款](#授權條款)**: GNU General Public License v3.0

---

<h2><a id="screenshots"></a>螢幕截圖</h2>

<img src="https://raw.githubusercontent.com/ezn24/Sponsor-Skip/refs/heads/bilibili/assets/screenshots/Screenshot1.jpg" alt="首頁" width="30%" /> <img src="https://raw.githubusercontent.com/ezn24/Sponsor-Skip/refs/heads/bilibili/assets/screenshots/Screenshot2.jpg" alt="設定頁面" width="30%" /> <img src="https://raw.githubusercontent.com/ezn24/Sponsor-Skip/refs/heads/bilibili/assets/screenshots/Screenshot3.jpg" alt="其他設定" width="30%" />

---

## 功能

* **Bilibili 支援：** 為 Android 上的 Bilibili 播放體驗加入自動跳過功能，不需要修改 Bilibili APK。

* **基於 BilibiliSponsorBlock：** 使用由 [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock) 專案及其社群建立的片段資料。

* **自動跳過片段：** 播放影片時，自動取得已由社群標記的時間片段，並在播放進度進入指定片段時跳至片段結尾。

* **多種片段分類：** 可依照 BilibiliSponsorBlock 提供的片段分類，自訂需要跳過或忽略的內容，例如：

  * 廣告
  * 無償／自我推廣
  * 柔性推廣／品牌合作
  * 三連／訂閱提醒
  * 精彩時刻／重點
  * 過場／開場動畫
  * 鳴謝／結束畫面
  * 回顧／概要
  * 離題閒聊／玩笑
  * 音樂：非音樂部分
  * 靜音片段

* **節省時間統計：** 記錄已跳過的片段數量以及累計節省的觀看時間。

* **備份與還原：** 備份及還原應用程式偏好設定與統計資料。

* **最短片段長度：** 可設定片段最低長度，忽略過短的片段。

* **跳過偏移：** 可調整跳過時間點，使跳轉稍微提前或延後，以因應不同裝置的播放延遲。

* **快速開關：** 可快速啟用或停用 Sponsor Skip for Bilibili 服務。

* **現代化介面：** 採用簡潔的 Android Material Design 介面。

* **開放原始碼：** Sponsor Skip for Bilibili 以 GPL-3.0 授權釋出，原始碼公開於 GitHub。

---

## 運作方式

**Sponsor Skip for Bilibili** 將 Android 的媒體控制能力與 [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock) 提供的片段資料結合，在不修改 Bilibili 應用程式本體的情況下，自動跳過已標記的影片片段。

基本流程如下：

1. **偵測播放狀態**

   Sponsor Skip for Bilibili 透過 Android 系統提供的媒體相關 API，偵測目前正在播放的 Bilibili 內容以及播放進度。

2. **識別影片**

   應用程式根據目前取得的媒體資訊，解析對應的 Bilibili 影片。

3. **取得片段資料**

   Sponsor Skip for Bilibili 向 BilibiliSponsorBlock 所使用的服務取得該影片已由社群提交的片段時間資訊。

4. **監控播放進度**

   播放影片期間，Sponsor Skip for Bilibili 持續追蹤目前的播放位置。

5. **自動跳過**

   當播放位置進入設定為自動跳過的片段時，Sponsor Skip for Bilibili 會透過 Android 媒體控制功能將播放位置移動至該片段結尾。

因此不需要修改 Bilibili APK，也不需要將 Sponsor Skip for Bilibili 直接注入 Bilibili 應用程式。

---

## 下載

你可以從 GitHub Releases 下載最新版本：

<table>
  <tr>
    <th align="center">GitHub Releases</th>
  </tr>
  <tr>
    <td align="center">
      <a href="https://github.com/ezn24/Sponsor-Skip/releases/latest">
        <img src="https://raw.githubusercontent.com/NeoApplications/Neo-Backup/034b226cea5c1b30eb4f6a6f313e4dadcbb0ece4/badge_github.png" alt="從 GitHub Releases 下載" height="70">
      </a>
    </td>
  </tr>
</table>

> 建議僅從本專案的 GitHub Releases 或其他明確列出的官方來源下載 APK。

---

## 從原始碼建置

如果你希望自行編譯 Sponsor Skip for Bilibili，可以使用專案內附的 Gradle Wrapper。

### 必要環境

* **Git**
* **JDK 17** 或更新版本
* **Android SDK**
* 已設定 `ANDROID_HOME`
* 已接受 Android SDK 授權條款

例如：

```bash
git clone -b bilibili https://github.com/ezn24/Sponsor-Skip.git
cd Sponsor-Skip
./gradlew assembleDebug
```

編譯完成後，可在 Gradle 對應的輸出目錄找到 APK。

---

## 貢獻

歡迎參與 Sponsor Skip for Bilibili 的開發。

1. Fork 此 Repository。
2. 建立新的功能分支。

```bash
git checkout -b feature/AmazingFeature
```

3. 提交修改。

```bash
git commit -m "feat: add AmazingFeature"
```

4. 推送分支。

```bash
git push origin feature/AmazingFeature
```

5. 建立 Pull Request。

如果修改涉及 BilibiliSponsorBlock 的 API、片段分類或資料格式，建議同時參考：

* [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock)
* [BilibiliSponsorBlock API 文件](https://github.com/hanydd/BilibiliSponsorBlock/wiki/API)

---

## 問題回報與功能建議

### 發現問題？

請前往：

[GitHub Issues](https://github.com/ezn24/Sponsor-Skip/issues)

建立 Issue，並盡可能提供：

* Sponsor Skip for Bilibili 版本
* Android 版本
* Bilibili 版本
* 裝置型號
* 問題重現步驟
* 預期結果
* 實際結果
* 必要的錯誤記錄或截圖

### 有功能建議？

同樣可以透過：

[GitHub Issues](https://github.com/ezn24/Sponsor-Skip/issues)

提出你的想法。

---

## 技術資訊

### 技術堆疊

![Kotlin](https://img.shields.io/badge/Kotlin-%237F52FF.svg?style=for-the-badge\&logo=kotlin\&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge\&logo=android\&logoColor=white)
![Coroutines](https://img.shields.io/badge/Coroutines-0095D5?style=for-the-badge\&logo=kotlin\&logoColor=white)
![Material Design](https://img.shields.io/badge/Material_Design-757575?style=for-the-badge\&logo=materialdesign\&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge\&logo=Gradle\&logoColor=white)
![BilibiliSponsorBlock](https://img.shields.io/badge/BilibiliSponsorBlock-00A1D6?style=for-the-badge)

### 核心架構

Sponsor Skip for Bilibili 主要以 **Kotlin** 開發，利用 Android 系統提供的媒體功能，在不修改 Bilibili 本體的情況下控制目前正在播放的內容。

核心流程可以概括為：

* **媒體播放偵測**

  取得目前 Android 系統中的媒體播放資訊，判斷 Bilibili 是否正在播放影片。

* **影片識別**

  根據媒體資訊解析目前播放的 Bilibili 影片，以取得查詢片段資料所需的影片識別資訊。

* **BilibiliSponsorBlock API**

  取得由 BilibiliSponsorBlock 社群提交的片段時間與分類資料。

  API 文件：

  https://github.com/hanydd/BilibiliSponsorBlock/wiki/API

* **播放進度追蹤**

  Sponsor Skip for Bilibili 在背景追蹤目前影片播放位置，判斷是否即將或已經進入需要跳過的片段。

* **Android MediaController**

  當播放位置進入符合設定的片段時，透過 Android 的媒體控制功能將播放器跳轉至片段結束位置。

* **非同步處理**

  網路請求、播放狀態追蹤以及其他背景工作透過 Kotlin Coroutines 執行，避免阻塞主 UI 執行緒。

---

## BilibiliSponsorBlock

Sponsor Skip for Bilibili 的 Bilibili 支援是基於：

**[hanydd/BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock)**

BilibiliSponsorBlock 是一個移植自 SponsorBlock、專門針對 Bilibili 設計的開源專案。

它建立了一套由社群共同維護的影片片段資料，讓使用者可以標記並跳過：

* 廣告
* 自我推廣
* 品牌合作
* 三連提醒
* 開場／過場
* 結束畫面
* 回顧
* 離題內容
* 其他可分類片段

BilibiliSponsorBlock 同時提供 API，使其他客戶端及第三方專案可以使用這些社群建立的片段資料。

### BilibiliSponsorBlock API

正式服務：

```text
https://bsbsb.top/api/
```

API 文件：

https://github.com/hanydd/BilibiliSponsorBlock/wiki/API

### 資料庫

BilibiliSponsorBlock 亦公開其片段資料供二次開發使用：

```text
https://download.bsbsb.top/database.zip
```

---

## 致謝

Sponsor Skip for Bilibili 的 Bilibili 功能建立在多個開源專案及社群成果之上。

### 1. Sponsor-Skip

**[Sponsor-Skip](https://github.com/jaival-11/Sponsor-Skip)**
由 [Jaival-11](https://github.com/jaival-11) 建立及維護

基於其項目並經過修改以適配 BiliBili，總體原理類似

### 2. BilibiliSponsorBlock

**[BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock)**
由 [hanydd](https://github.com/hanydd) 建立及維護。

Sponsor Skip for Bilibili 的 Bilibili 片段資料、分類概念以及相關 API 支援基於 BilibiliSponsorBlock。

感謝 BilibiliSponsorBlock 的開發者以及所有協助提交、修正與審核影片片段的社群成員。

### 3. SponsorBlock

**[SponsorBlock](https://github.com/ajayyy/SponsorBlock)**
由 [Ajay Ramachandran](https://github.com/ajayyy) 建立。

BilibiliSponsorBlock 本身移植並改造自 SponsorBlock。SponsorBlock 建立了社群協作標記影片片段的核心概念及原始實作。

因此 Sponsor Skip for Bilibili 亦間接受益於 SponsorBlock 及其開源社群的工作。

### 4. Kotlin 與 Android

**[Kotlin](https://kotlinlang.org/)** 與 **[Android](https://developer.android.com/)**

提供 Sponsor Skip for Bilibili 所使用的程式語言、Android API、背景處理機制與應用程式架構。

### 5. Android Jetpack

**[Android Jetpack](https://developer.android.com/jetpack)**

提供 Android 應用程式生命週期、UI 與其他基礎架構元件。

### 5. Material Design

**[Material Design](https://m3.material.io/)**

Sponsor Skip for Bilibili 的介面與部分圖示使用 Material Design 系統。

---

## 隱私權

Sponsor Skip for Bilibili 不以蒐集使用者資料為目的。

應用程式可能需要存取必要的 Android 系統媒體資訊以及向 Bilibili、BilibiliSponsorBlock 或相關服務發出網路請求，以識別影片及取得片段資料。

完整內容請參閱：

[Privacy Policy](PRIVACY.md)

---

## 免責聲明

### BilibiliSponsorBlock

Sponsor Skip for Bilibili 使用或相容於由 BilibiliSponsorBlock 提供的片段資料及相關服務。

BilibiliSponsorBlock 是獨立的第三方開源專案。

Sponsor Skip for Bilibili 與 BilibiliSponsorBlock 的原作者之間不存在官方從屬關係，除非另有明確說明。

### 責任與保固

本程式為自由軟體，你可以依照自由軟體基金會發布的 **GNU General Public License** 條款重新散布及／或修改本程式；授權版本為 GPL 第 3 版或任何較新的版本。

本程式的發布目的是希望它能夠有所幫助，但**不提供任何形式的保固**，包括但不限於適售性或特定用途適用性的默示保固。

詳細內容請參閱 GNU General Public License。

### 使用者責任與平台服務條款

Sponsor Skip for Bilibili 是一個本機自動化及媒體控制工具。

使用本軟體即代表你理解並同意：

* 你有責任確認自己的使用方式符合 Bilibili 及其他第三方服務的使用條款。
* 第三方平台可能隨時修改 API、播放器行為、服務條款或技術限制。
* Sponsor Skip for Bilibili 無法保證任何第三方服務永久相容。
* 專案維護者不對因使用本軟體造成的帳號限制、服務中斷、資料損失或其他損害負責。

---

## 授權條款

Sponsor Skip for Bilibili 採用 [GNU General Public License v3.0](LICENSE) 授權。

本專案包含或衍生自其他 GPL 授權專案之概念或程式碼時，相關內容仍應遵循其原始授權條款與著作權聲明。

特別感謝：

* [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock)
* [SponsorBlock](https://github.com/ajayyy/SponsorBlock)
* 

---

<div align="center">

**基於 [BilibiliSponsorBlock](https://github.com/hanydd/BilibiliSponsorBlock)**

**Maintained by [ezn24](https://github.com/ezn24)**

</div>
