# Architecture Documentation

## Clean Architecture Pattern

يتبع المشروع نمط Clean Architecture مع تقسيم واضح للمسؤوليات:

### Layer 1: Presentation Layer (UI)
- **المكونات**: Activities, Composables, ViewModels
- **المسؤوليات**: عرض البيانات وتلقي المدخلات من المستخدم
- **الموقع**: `ui/` و `ui/viewmodel/`

### Layer 2: Domain Layer
- **المكونات**: Use Cases, Entities
- **المسؤوليات**: منطق التطبيق المستقل عن الإطار العمل
- **الموقع**: `domain/usecase/`

### Layer 3: Data Layer
- **المكونات**: Repositories, DAOs, APIs, Local Database
- **المسؤوليات**: جلب وتخزين البيانات من مصادر مختلفة
- **الموقع**: `data/`

## Design Patterns

### 1. MVVM (Model-View-ViewModel)
استخدام ViewModels لإدارة حالة الواجهة وربط البيانات.

### 2. Repository Pattern
التعامل مع مصادر البيانات المختلفة من خلال واجهة واحدة.

### 3. Dependency Injection
استخدام Hilt لحقن المتعلقات.

### 4. Flow & StateFlow
لإدارة الحالة والبيانات غير المتزامنة.

## Data Flow

```
User Action (UI)
    ↓
ViewModel (processIntent)
    ↓
UseCase (processBusiness Logic)
    ↓
Repository (getData)
    ↓
Data Sources (API/DB)
    ↓
Return Result
    ↓
ViewModel (updateState)
    ↓
Recompose UI
```

## File Organization

```
com/filerecovery/
├── data/                    # Data Layer
│   ├── api/                 # External APIs
│   │   ├── AuthService.kt
│   │   └── EmailService.kt
│   ├── db/                  # Local Database
│   │   ├── FileRecoveryDatabase.kt
│   │   ├── UserDao.kt
│   │   ├── EmailAccountDao.kt
│   │   └── RecoveredFileDao.kt
│   ├── model/               # Data Models
│   │   ├── User.kt
│   │   ├── EmailAccount.kt
│   │   └── RecoveredFile.kt
│   └── security/            # Encryption & Security
│       └── TokenManager.kt
├── domain/                  # Domain Layer
│   └── usecase/
│       └── FileRecoveryUseCase.kt
├── service/                 # Background Services
│   ├── EmailSyncService.kt
│   └── FileProcessingService.kt
├── receiver/                # Broadcast Receivers
│   └── SyncReceiver.kt
└── ui/                      # Presentation Layer
    ├── theme/
    │   ├── Theme.kt
    │   └── Type.kt
    ├── viewmodel/
    │   └── FileRecoveryViewModel.kt
    └── MainActivity.kt
```

## State Management

استخدام MutableStateFlow و StateFlow للتحكم بحالة الواجهة:

```kotlin
private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
val uiState: StateFlow<UiState> = _uiState.asStateFlow()
```

## Async Operations

استخدام Coroutines و Flow للعمليات غير المتزامنة:

```kotlin
viewModelScope.launch {
    fileRecoveryUseCase.getFilteredFiles(accountId, filter)
        .collect { files ->
            _recoveredFiles.value = files
        }
}
```

## Database Schema

### Entity Relationships
- User (1) ──── (Many) EmailAccount
- EmailAccount (1) ──── (Many) RecoveredFile

### Indexes
تم إضافة فهرسة على الأعمدة المتكررة للبحث السريع:
- `RecoveredFile.emailAccountId`
- `RecoveredFile.fileHash`
- `RecoveredFile.dateReceived`

## Security

### Token Encryption
جميع رموز البريد الإلكتروني مشفرة باستخدام:
- AES-256-GCM
- EncryptedSharedPreferences

### Permissions
- المدى الأدنى من الأذونات المطلوبة
- طلب الأذونات عند الحاجة (Runtime Permissions)

### Network Security
- استخدام HTTPS فقط
- تحقق من شهادات SSL
- بدون cleartext traffic

## Performance

### Optimization Strategies
1. **Database Queries**: استخدام LIMIT و pagination
2. **Image Loading**: تحميل صور مصغرة بدلاً من الملفات الكاملة
3. **Memory Management**: استخدام WeakReference للمراجع المؤقتة
4. **Coroutines**: عدم حظر UI thread

### Caching
- Room Database للتخزين المحلي
- EncryptedSharedPreferences للبيانات الحساسة
- In-memory cache للبيانات المتكررة

## Error Handling

استخدام Result<T> pattern للتعامل مع الأخطاء:

```kotlin
suspend fun scanEmailAttachments(): Result<List<RecoveredFile>> = runCatching {
    // Process
    recoveredFiles
}
```

## Testing

### Unit Tests
- اختبار ViewModels
- اختبار Use Cases
- اختبار Data Access

### Integration Tests
- اختبار API Calls
- اختبار Database Operations

### UI Tests
- اختبار Composables
- اختبار User Interactions

## Future Improvements

1. **Machine Learning**: كشف ذكي للملفات المهمة
2. **Cloud Sync**: تحسين مزامنة الخدمات السحابية
3. **Offline Mode**: العمل بدون اتصال إنترنت
4. **Advanced Analytics**: تحليلات استخدام معقدة
5. **Multi-language Support**: دعم لغات متعددة
