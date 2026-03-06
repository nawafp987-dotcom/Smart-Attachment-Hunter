# الميزات الرئيسية - File Recovery Pro

## 1. ربط حسابات البريد الإلكتروني المتعددة

### القدرات
- ✓ ربط عدة حسابات Gmail في نفس الوقت
- ✓ ربط عدة حسابات Outlook في نفس الوقت
- ✓ إدارة موحدة لجميع الحسابات
- ✓ مزامنة مستقلة لكل حساب

### الفوائد
- استعادة الملفات من جميع حساباتك
- عدم فقدان أي ملفات مهمة
- لوحة معلومات موحدة
- سهولة الإدارة والتتبع

### التنفيذ التقني
```kotlin
// إضافة حساب Gmail
val result = authService.authenticateGmail(
    code = "authorization_code",
    clientId = GMAIL_CLIENT_ID,
    clientSecret = GMAIL_CLIENT_SECRET,
    redirectUri = REDIRECT_URI
)

// الحسابات المتعددة في قاعدة البيانات
emailAccountDao.getAllEmailAccounts()
```

---

## 2. استخراج ذكي للملفات

### الخصائص
- ✓ فحص تلقائي للمرفقات
- ✓ استخراج البيانات الوصفية
- ✓ تصنيف الملفات حسب النوع
- ✓ معالجة الملفات الكبيرة

### أنواع الملفات المدعومة
| النوع | الصيغ |
|-------|------|
| الصور | JPG, PNG, GIF, BMP, WebP, SVG |
| الفيديوهات | MP4, AVI, MKV, MOV, FLV, WMV |
| المستندات | PDF, DOC, DOCX, XLS, XLSX, PPT |
| الصوت | MP3, WAV, M4A, FLAC, AAC, OGG |
| الأرشيفات | ZIP, RAR, 7Z, TAR, GZ, BZ2 |

### المثال
```kotlin
// استخراج الملفات من حساب
val files = emailService.scanEmailAttachments(emailAccount)
files.forEach { file ->
    println("File: ${file.fileName}, Type: ${file.fileType}")
}
```

---

## 3. كشف وإزالة الملفات المكررة

### الآلية
- ✓ حساب بصمة SHA-256 لكل ملف
- ✓ مقارنة البصمات تلقائياً
- ✓ تحديد الملفات المكررة
- ✓ إزالة آمنة للنسخ الزائدة

### الفوائد
- توفير مساحة التخزين
- تجنب التحميل المتكرر
- تحسين الأداء
- إدارة أنظف للملفات

### التنفيذ
```kotlin
// كشف الملفات المكررة
fileRecoveryUseCase.detectDuplicates(accountId)

// الملفات المكررة تُحفظ مع علامة isDuplicate = true
// والملف الأصلي يُحفظ مع duplicateOf = originalId
```

---

## 4. البحث والتصفية المتقدمة

### معاييرالبحث
- ✓ البحث بالاسم
- ✓ البحث بالمُرسل
- ✓ البحث بموضوع الرسالة

### معاييرالتصفية
| المعيار | الخيارات |
|--------|---------|
| **نوع الملف** | صور، فيديو، مستند، صوت، أرشيف |
| **نطاق التاريخ** | من تاريخ - إلى تاريخ |
| **حجم الملف** | من حجم - إلى حجم |
| **المُرسل** | بريد محدد أو جميع الرسائل |
| **استبعاد المكررات** | نعم / لا |

### المثال
```kotlin
val filter = FileFilter(
    fileTypes = listOf(FileType.IMAGE, FileType.VIDEO),
    startDate = Date(2023, 0, 1).time,
    endDate = Date(2024, 0, 1).time,
    minSize = 1024 * 1024, // 1 MB
    maxSize = 100 * 1024 * 1024, // 100 MB
    excludeDuplicates = true
)

viewModel.applyFilter(filter)
```

---

## 5. معاينة آمنة للملفات

### الميزات
- ✓ توليد صور مصغرة سريعة
- ✓ معاينة قبل التحميل
- ✓ حماية الخصوصية
- ✓ معالجة محلية فقط

### الفوائد
- عدم تحميل الملفات الكبيرة مباشرة
- معرفة محتوى الملف قبل الحفظ
- توفير النطاق الترددي
- أمان أفضل

### التنفيذ
```kotlin
// توليد صور مصغرة
val thumbnail = fileProcessingService.generateThumbnail(
    filePath = "path/to/file.jpg",
    width = 128,
    height = 128
)

// عرض الصورة المصغرة في الواجهة
displayThumbnail(thumbnail)
```

---

## 6. تحميل وحفظ ذكي

### خيارات الحفظ
- ✓ حفظ في التخزين المحلي
- ✓ نقل مباشر للسحابة (Google Drive, OneDrive)
- ✓ إدارة المساحة التلقائية
- ✓ ضغط ذكي للملفات

### معالجة الملفات الكبيرة
```kotlin
// تحميل آمن مع معالجة الأخطاء
val result = fileProcessingService.downloadFile(
    url = downloadUrl,
    fileName = "document.pdf",
    targetDir = File("/Downloads/FileRecovery")
)

// معالجة النتيجة
result.onSuccess { file ->
    println("Downloaded: ${file.name}")
}.onFailure { error ->
    println("Error: ${error.message}")
}
```

---

## 7. إدارة التخزين والمساحة

### المميزات
- ✓ تتبع استخدام المساحة
- ✓ تحذيرات عند امتلاء التخزين
- ✓ اقتراح حذف الملفات القديمة
- ✓ نقل للسحابة عند الامتلاء

### الحدود
- حد تخزين افتراضي: 10 GB
- حد ملف واحد: 2 GB
- تحذير عند: 90% استخدام

---

## 8. الأمان والخصوصية

### تدابير الأمان
- ✓ تشفير AES-256 للرموز
- ✓ معالجة آمنة للبيانات الحساسة
- ✓ عدم تخزين الكلمات المرورية
- ✓ بدون logging للبيانات الحساسة

### معايير الخصوصية
- ✓ معالجة محلية للبيانات
- ✓ عدم مشاركة البيانات مع طرف ثالث
- ✓ حذف آمن للبيانات
- ✓ سياسة خصوصية واضحة

### التنفيذ
```kotlin
// تشفير الرموز
val encryptedToken = tokenManager.encryptToken(accessToken)

// حفظ آمن
tokenManager.saveToken("gmail_access_token", encryptedToken)

// استرجاع آمن
val decryptedToken = tokenManager.getToken("gmail_access_token")
```

---

## 9. تنبيهات وإشعارات

### أنواع التنبيهات
- ✓ تنبيهات المزامنة
- ✓ تنبيهات التحميل
- ✓ تنبيهات الأخطاء
- ✓ تنبيهات التخزين

### الإشعارات
```kotlin
// إشعار مزامنة
val notification = NotificationCompat.Builder(this, "sync_channel")
    .setContentTitle("File Recovery Pro")
    .setContentText("جاري المزامنة...")
    .setSmallIcon(R.drawable.ic_launcher_foreground)
    .setProgress(100, progress, false)
    .build()

startForeground(SYNC_NOTIFICATION_ID, notification)
```

---

## 10. المزامنة الخلفية

### خصائص المزامنة
- ✓ مزامنة دورية تلقائية
- ✓ مزامنة عند الاتصال بالإنترنت
- ✓ مزامنة يدوية
- ✓ توقف الجهاز بدون فقدان البيانات

### إعدادات المزامنة
```kotlin
// فترة المزامنة: ساعة واحدة
const val SYNC_INTERVAL = 60 * 60 * 1000L

// تجنب مزامنة متعددة متزامنة
fun triggerSync() {
    val intent = Intent(context, EmailSyncService::class.java)
    context.startService(intent)
}
```

---

## 11. إدارة الجلسات (Sessions)

### معالجة الرموز
- ✓ تحديث تلقائي للرموز المنتهية
- ✓ معالجة تعطل الاتصال
- ✓ إعادة محاولة ذكية
- ✓ تسجيل خروج آمن

---

## 12. دعم متعدد اللغات (قادم)

### اللغات المخطط دعمها
- العربية ✓ (حالياً)
- الإنجليزية ✓ (حالياً)
- الفرنسية (قريباً)
- الإسبانية (قريباً)
- الصينية (قريباً)
- اليابانية (قريباً)

---

## مقارنة الميزات

| الميزة | الإصدار 1.0 | الإصدار 1.1 | الإصدار 2.0 |
|--------|-----------|-----------|-----------|
| Gmail | ✓ | ✓ | ✓ |
| Outlook | ✓ | ✓ | ✓ |
| Google Drive | - | ✓ | ✓ |
| OneDrive | - | ✓ | ✓ |
| Dropbox | - | - | ✓ |
| Machine Learning | - | - | ✓ |
| Multi-language | العربية فقط | 3 لغات | 10+ لغات |
| Advanced Analytics | - | ✓ | ✓ |
| Offline Mode | - | - | ✓ |

---

## أمثلة الاستخدام

### مثال 1: ربط حساب Gmail
```kotlin
val result = authService.authenticateGmail(
    code = "authorization_code",
    clientId = BuildConfig.GMAIL_CLIENT_ID,
    clientSecret = BuildConfig.GMAIL_CLIENT_SECRET,
    redirectUri = BuildConfig.REDIRECT_URI
)

result.onSuccess { emailAccount ->
    emailAccountDao.insertEmailAccount(emailAccount)
}
```

### مثال 2: البحث عن صور من سنة محددة
```kotlin
val filter = FileFilter(
    fileTypes = listOf(FileType.IMAGE),
    startDate = Calendar.getInstance().apply {
        set(2023, Calendar.JANUARY, 1)
    }.timeInMillis,
    endDate = Calendar.getInstance().apply {
        set(2023, Calendar.DECEMBER, 31)
    }.timeInMillis
)

viewModel.applyFilter(filter)
```

### مثال 3: تحميل ملف مع معالجة الخطأ
```kotlin
viewModel.markFileAsDownloaded(
    fileId = file.id,
    downloadPath = "/storage/emulated/0/Download/file.pdf"
)
```

---

## الخلاصة

File Recovery Pro توفر مجموعة شاملة من الميزات لاستعادة الملفات بأمان وكفاءة:

✓ **قوية**: تدعم Gmail و Outlook و خدمات أخرى
✓ **آمنة**: تشفير نهاية لنهاية
✓ **ذكية**: كشف تلقائي للملفات المكررة
✓ **سهلة**: واجهة بديهية وسهلة الاستخدام
✓ **مرنة**: قابلة للتوسع والتطوير

---

للمزيد من المعلومات، يرجى قراءة:
- [README_AR.md](README_AR.md)
- [ARCHITECTURE.md](ARCHITECTURE.md)
- [SETUP.md](SETUP.md)
