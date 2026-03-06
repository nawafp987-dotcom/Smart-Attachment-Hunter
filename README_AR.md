# File Recovery Pro

تطبيق Android متقدم لاستعادة الملفات المفقودة من حسابات البريد الإلكتروني والخدمات السحابية.

## الميزات الرئيسية

### 1. دعم البريد الإلكتروني المتعدد
- ربط عدة حسابات Gmail و Outlook في نفس الوقت
- لوحة معلومات موحدة لعرض الملفات المستعادة من جميع الحسابات
- مزامنة تلقائية وفي الوقت الفعلي

### 2. البحث والتصفية الذكي
- البحث بناءً على فترات زمنية محددة
- تصنيف الملفات حسب الأنواع (صور، فيديوهات، مستندات، تسجيلات مكالمات)
- تصفية حسب حجم الملف والبريد المُرسل

### 3. معاينة آمنة للملفات
- توليد صور مصغرة سريعة
- معاينة الملفات قبل التحميل
- حماية الخصوصية من خلال المعالجة المحلية

### 4. إدارة البيانات الذكية
- كشف ومنع تكرار الملفات
- حفظ الملفات مباشرة في الخدمات السحابية
- تتبع الملفات المحملة والمفقودة

### 5. الأمان والخصوصية
- تشفير نهاية لنهاية لرموز البريد الإلكتروني
- معالجة البيانات محلياً فقط
- لا تخزين البيانات الحساسة على السيرفر

## البنية التقنية

### التكنولوجيات المستخدمة
- **Kotlin**: لغة البرمجة الأساسية
- **Jetpack Compose**: واجهة المستخدم الحديثة
- **Room Database**: لتخزين البيانات محلياً
- **Retrofit**: للتواصل مع APIs البريد
- **Supabase**: لتخزين البيانات والمصادقة
- **Coroutines**: للعمليات غير المتزامنة

### بنية المشروع
```
app/src/main/
├── java/com/filerecovery/
│   ├── data/
│   │   ├── api/          # خدمات البريد والمصادقة
│   │   ├── db/           # قاعدة البيانات المحلية
│   │   ├── model/        # نماذج البيانات
│   │   └── security/     # إدارة التشفير والأمان
│   ├── domain/
│   │   └── usecase/      # منطق التطبيق الأساسي
│   ├── service/          # خدمات الخلفية
│   ├── receiver/         # معالجات الأحداث
│   └── ui/
│       ├── theme/        # التصميم والألوان
│       ├── viewmodel/    # إدارة حالة الواجهة
│       └── MainActivity.kt
└── res/
    ├── values/          # النصوص والألوان
    └── xml/             # إعدادات النظام
```

## ملفات البيانات الرئيسية

### الجداول الرئيسية
1. **users**: بيانات المستخدم
2. **email_accounts**: حسابات البريد المرتبطة
3. **recovered_files**: الملفات المستعادة

## الأذونات المطلوبة
- `INTERNET`: للتواصل مع APIs البريد
- `READ_EXTERNAL_STORAGE`: لقراءة الملفات
- `WRITE_EXTERNAL_STORAGE`: لكتابة الملفات المحملة
- `GET_ACCOUNTS`: لقائمة حسابات البريد
- `CAMERA`: اختياري للتصوير

## البدء

### المتطلبات
- Android 8.0 (API level 26) أو أعلى
- Android Studio 2022.1 أو أعلى
- Gradle 8.0

### التثبيت
1. استنساخ المشروع
2. فتح في Android Studio
3. تنزيل المكتبات: `Build > Make Project`
4. تشغيل التطبيق على جهاز أو محاكي

### الإعدادات الضرورية
قبل تشغيل التطبيق، يجب إضافة مفاتيح API:

1. **Gmail API**:
   - إنشاء مشروع في Google Cloud Console
   - تفعيل Gmail API
   - إنشاء OAuth 2.0 credentials

2. **Outlook API**:
   - تسجيل التطبيق في Azure Portal
   - الحصول على Client ID و Client Secret

3. **Supabase**:
   - إنشاء مشروع في Supabase
   - نسخ مفاتيح الاتصال في `.env`

## هيكل قاعدة البيانات

### جدول email_accounts
```sql
- id: PrimaryKey
- email: String
- provider: GMAIL | OUTLOOK | IMAP
- accessToken: String (مشفر)
- refreshToken: String (مشفر)
- tokenExpiry: Long
- syncStatus: IDLE | SYNCING | COMPLETED | ERROR
- lastSyncTime: Long
```

### جدول recovered_files
```sql
- id: PrimaryKey
- emailAccountId: ForeignKey
- fileName: String
- fileType: IMAGE | VIDEO | DOCUMENT | AUDIO | ARCHIVE
- fileSize: Long
- fileHash: String (SHA-256)
- downloadUrl: String
- dateReceived: Long
- senderEmail: String
- isDuplicate: Boolean
- isDownloaded: Boolean
```

## التطور والمساهمة

### إضافة ميزات جديدة
1. إنشاء فرع جديد: `git checkout -b feature/feature-name`
2. تطوير الميزة
3. إرسال pull request

### معايير الكود
- استخدام Kotlin idioms
- اتباع Material Design 3
- كتابة اختبارات الوحدة
- توثيق الدوال العامة

## الترخيص
حقوق محفوظة 2024 - File Recovery Pro

## الدعم والتواصل
للإبلاغ عن مشاكل أو اقتراح ميزات، يرجى فتح issue أو تقديم pull request.
