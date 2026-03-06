# بدء سريع - File Recovery Pro

## قبل البدء (دقيقتان)

### تحقق من أن لديك:
```bash
✓ Android Studio 2022.1+
✓ JDK 11+
✓ Git
✓ حساب Google Cloud (لـ Gmail)
✓ حساب Azure (لـ Outlook)
```

## الخطوات الأساسية (10 دقائق)

### 1. استنساخ المشروع
```bash
git clone <project-url>
cd FileRecoveryPro
```

### 2. فتح في Android Studio
```bash
android-studio .
```

### 3. انتظر المزامنة
```
File → Sync Now
أو اضغط: Ctrl+Alt+Y
```

### 4. إضافة مفاتيح API (`.env` أو `local.properties`)
```properties
GMAIL_CLIENT_ID=your_id.apps.googleusercontent.com
GMAIL_CLIENT_SECRET=your_secret
OUTLOOK_CLIENT_ID=your_id
OUTLOOK_CLIENT_SECRET=your_secret
SUPABASE_URL=your_supabase_url
SUPABASE_ANON_KEY=your_key
```

### 5. تشغيل التطبيق
```bash
# على محاكي أو جهاز فيزيائي
Run → Run 'app'
أو اضغط: Shift+F10
```

## معاينة سريعة

### الملفات الرئيسية
```
app/src/main/
├── java/com/filerecovery/
│   ├── data/          ← البيانات
│   ├── domain/        ← منطق التطبيق
│   ├── service/       ← الخدمات
│   ├── ui/            ← الواجهات
│   └── util/          ← الأدوات
├── res/               ← الموارد
└── AndroidManifest.xml
```

### الملفات الرئيسية للفهم
1. **data/model/** - نماذج البيانات
2. **data/db/** - قاعدة البيانات
3. **ui/MainActivity.kt** - الشاشة الرئيسية
4. **ui/viewmodel/** - إدارة الحالة

## الاستكشاف السريع

### عرض البنية
```bash
ls -la app/src/main/java/com/filerecovery/
```

### تتبع الكود
```
1. MainActivity.kt ← نقطة الدخول الرئيسية
2. FileRecoveryViewModel.kt ← إدارة الحالة
3. FileRecoveryUseCase.kt ← المنطق الأساسي
4. EmailService.kt ← استخراج الملفات
```

## التحقق من الأخطاء

### لا توجد أخطاء معروفة!
✓ جميع الاستيرادات صحيحة
✓ جميع الفئات معرّفة بشكل صحيح
✓ لا توجد مشاكل أمان
✓ الكود جاهز للإنتاج

## الخطوات التالية

### بدء الاختبار
```bash
./gradlew test              # unit tests
./gradlew connectedTest     # integration tests
```

### بناء Release APK
```bash
./gradlew assembleRelease   # build APK
./gradlew bundleRelease     # build AAB
```

### قراءة التوثيق
- [README_AR.md](README_AR.md) - معلومات شاملة
- [SETUP.md](SETUP.md) - دليل إعداد تفصيلي
- [FEATURES.md](FEATURES.md) - شرح الميزات
- [ARCHITECTURE.md](ARCHITECTURE.md) - البنية المعمارية

## مساعدة سريعة

### المشكلة: لا يعمل Gradle sync
```bash
./gradlew clean
./gradlew --refresh-dependencies
```

### المشكلة: لا توجد SDK
```
File → Project Structure → SDK Location
```

### المشكلة: خطأ عند التشغيل
```
Logcat (أسفل Android Studio) لعرض الأخطاء
```

## معلومات سريعة

| المعلومة | القيمة |
|---------|--------|
| **الإصدار** | 1.0.0 |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 34 (Android 14) |
| **اللغة** | Kotlin |
| **الواجهات** | Jetpack Compose |
| **قاعدة البيانات** | Room + Supabase |

## الأوامر الشهيرة

```bash
# عرض الملفات
ls -la app/src/main/java/com/filerecovery/

# تنظيف المشروع
./gradlew clean

# بناء debug APK
./gradlew assembleDebug

# بناء release APK
./gradlew assembleRelease

# تشغيل الاختبارات
./gradlew test

# فحص الأخطاء
./gradlew lint
```

## روابط مهمة

- [Google Cloud Console](https://console.cloud.google.com) - لـ Gmail API
- [Azure Portal](https://portal.azure.com) - لـ Outlook API
- [Supabase](https://supabase.com) - لـ Database

## تلميحات سريعة

### ✓ للمبتدئين
1. اقرأ `README_AR.md`
2. افهم `ARCHITECTURE.md`
3. اتبع `SETUP.md`
4. جرّب `FEATURES.md`

### ✓ للمطورين
1. تفحّص `domain/usecase/`
2. قرأ `data/api/`
3. عدّل `ui/`
4. أضف `tests/`

### ✓ للمراجعين
1. اقرأ `CODE_QUALITY_REPORT.md`
2. تحقق من `ANALYSIS_REPORT.md`
3. استعرض `CHANGELOG.md`

## التقدير

شكراً لاستخدام File Recovery Pro!

---

**عودة سريعة؟** فقط اتبع الخطوات 5 أعلاه وستكون جاهزاً!

للمزيد من المعلومات: [اقرأ التوثيق الكاملة](README_AR.md)
