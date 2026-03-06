# دليل الإعداد والتثبيت

## المتطلبات الأساسية

### متطلبات النظام
- نظام التشغيل: Windows, macOS, Linux
- Java Development Kit (JDK) 11+
- RAM: 8 GB على الأقل
- مساحة التخزين: 10 GB

### متطلبات البرمجيات
- Android Studio 2022.1 أو أحدث
- Gradle 8.0 أو أحدث
- Android SDK Level 34 (Target SDK)
- Min SDK Level 26 (Android 8.0)

## خطوات التثبيت

### 1. استنساخ المشروع
```bash
git clone https://github.com/yourusername/FileRecoveryPro.git
cd FileRecoveryPro
```

### 2. فتح في Android Studio
```bash
# Windows
android-studio.exe FileRecoveryPro

# macOS
open -a "Android Studio" FileRecoveryPro

# Linux
android-studio FileRecoveryPro
```

### 3. تحميل المكتبات
تجاهل الرسائل الأولية واتركها تكمل:
```bash
File → Sync Now
أو: Ctrl+Alt+Y (Windows/Linux) أو Cmd+Shift+Y (macOS)
```

### 4. إعدادات API

#### Gmail API Setup
1. انتقل إلى [Google Cloud Console](https://console.cloud.google.com)
2. أنشئ مشروع جديد
3. فعّل Gmail API:
   - انتقل إلى "APIs & Services" → "Library"
   - ابحث عن "Gmail API"
   - اضغط "Enable"

4. أنشئ OAuth 2.0 Credentials:
   - اذهب إلى "APIs & Services" → "Credentials"
   - اضغط "Create Credentials" → "OAuth 2.0 Client ID"
   - اختر "Android"
   - أدخل:
     - Package name: `com.filerecovery`
     - SHA-1 fingerprint: [سيتم إنشاؤه من Android Studio]

#### Microsoft Graph (Outlook) Setup
1. انتقل إلى [Azure Portal](https://portal.azure.com)
2. سجل تطبيق جديد:
   - Manage Azure AD → App registrations → New registration
   - Name: "File Recovery Pro"
   - Supported account types: "Accounts in any organizational directory..."

3. أضف API permissions:
   - API permissions → Add a permission
   - Microsoft Graph → Delegated permissions
   - اختر: Mail.Read, Mail.ReadWrite

4. أنشئ Client Secret:
   - Certificates & secrets → New client secret
   - انسخ القيمة

#### Supabase Setup
1. انتقل إلى [Supabase](https://supabase.com)
2. أنشئ مشروع جديد
3. سجل الدخول والانتظر لتشغيل المشروع
4. انسخ البيانات من "Project Settings" → "API":
   - Project URL
   - anon public key

### 5. إضافة مفاتيح API

تحديث `.env` في جذر المشروع:
```env
# Gmail
GMAIL_CLIENT_ID=your_client_id.apps.googleusercontent.com
GMAIL_CLIENT_SECRET=your_client_secret
GMAIL_REDIRECT_URI=http://localhost:8080/oauth2callback

# Outlook
OUTLOOK_CLIENT_ID=your_client_id
OUTLOOK_CLIENT_SECRET=your_client_secret
OUTLOOK_REDIRECT_URI=https://localhost

# Supabase
SUPABASE_URL=https://your-project.supabase.co
SUPABASE_ANON_KEY=your_anon_key
SUPABASE_SERVICE_ROLE_KEY=your_service_role_key
```

### 6. إنشاء التوقيع (Keystore)

لإنشاء توقيع لتطوير:
```bash
# اضغط على Build → Generate Signed Bundle / APK
# أو استخدم keytool مباشرة:
keytool -genkey -v -keystore ~/file_recovery.keystore \
  -keyalg RSA -keysize 2048 -validity 10000 \
  -alias file_recovery_key
```

## تشغيل التطبيق

### على محاكي Android
```bash
# سيفتح Android Studio مباشرة عند الضغط على Run
# أو استخدم:
adb emu kill
adb shell reboot bootloader
```

### على جهاز فيزيائي
1. فعّل Developer Mode:
   - اذهب إلى Settings
   - ابحث عن "Build number"
   - اضغط 7 مرات حتى تظهر رسالة "You are a developer"

2. فعّل USB Debugging:
   - اذهب إلى Settings → Developer options
   - فعّل "USB Debugging"

3. وصّل الجهاز عبر USB وشغّل:
```bash
./gradlew installDebug
```

## البناء والتوزيع

### بناء Debug APK
```bash
./gradlew assembleDebug
```
الملف الناتج: `app/build/outputs/apk/debug/app-debug.apk`

### بناء Release APK
```bash
./gradlew assembleRelease
```
الملف الناتج: `app/build/outputs/apk/release/app-release.apk`

### بناء Android App Bundle (للـ Google Play)
```bash
./gradlew bundleRelease
```
الملف الناتج: `app/build/outputs/bundle/release/app-release.aab`

## إصلاح المشاكل الشائعة

### مشكلة: Gradle sync فشل
**الحل**:
```bash
# نظّف المشروع
./gradlew clean

# حمّل الملفات مجدداً
./gradlew --refresh-dependencies

# أعد المزامنة
File → Sync Now
```

### مشكلة: لا يمكن العثور على Android SDK
**الحل**:
1. اذهب إلى File → Project Structure
2. اختر SDK Location
3. تأكد أن Android SDK Path محدد بشكل صحيح

### مشكلة: خطأ Kotlin compilation
**الحل**:
```bash
./gradlew clean assembleDebug
```

### مشكلة: توقع الناتج عند التشغيل
**الحل**:
1. تحقق من logcat للأخطاء
2. تأكد من أن جميع المكتبات محملة
3. قم بإعادة تشغيل Android Studio

## التطوير والاختبار

### تشغيل الاختبارات
```bash
# unit tests
./gradlew test

# instrumented tests
./gradlew connectedAndroidTest
```

### تحليل الكود
```bash
./gradlew lint
```

### معاينة التصميم
استخدم Jetpack Compose Preview مباشرة في Android Studio:
- افتح ملف Composable
- ابحث عن `@Preview` annotation
- اضغط على زر المعاينة

## الخطوات التالية

1. اقرأ [ARCHITECTURE.md](ARCHITECTURE.md) لفهم بنية المشروع
2. اقرأ [README_AR.md](README_AR.md) للمزيد عن الميزات
3. ابدأ بتطوير الميزات الجديدة
4. أضف اختبارات لكودك

## الحصول على الدعم

إذا واجهت مشاكل:
1. تحقق من الأخطاء في logcat
2. ابحث عن المشكلة في [Issues](https://github.com/yourusername/FileRecoveryPro/issues)
3. أنشئ issue جديدة مع تفاصيل المشكلة والـ stacktrace
