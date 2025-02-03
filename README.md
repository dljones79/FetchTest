# Fetch Test App

A modern Android application built with Jetpack Compose that demonstrates best practices in Android development, clean architecture, and comprehensive testing strategies.

## 🏗 Architecture

The app follows Clean Architecture principles with MVVM (Model-View-ViewModel) pattern:

```
app/
├── data/                 # Data layer
│   ├── remote/          # Remote data sources (API)
│   ├── repository/      # Repository implementations
│   └── model/           # Data models
├── domain/              # Domain layer
│   ├── model/           # Domain models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Use cases
└── ui/                  # Presentation layer
    ├── model/           # UI models
    ├── screen/          # Compose screens
    ├── theme/           # Theme and styling
    └── viewmodel/       # ViewModels

```

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern Android UI toolkit
- **Architecture Components**:
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
  - [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
  - [Hilt](https://dagger.dev/hilt/) for dependency injection
- **Networking**:
  - [Retrofit](https://square.github.io/retrofit/) for API calls
  - [OkHttp](https://square.github.io/okhttp/) for HTTP client
  - [Moshi](https://github.com/square/moshi) for JSON parsing
- **Asynchronous Programming**:
  - [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
  - [Kotlin Flow](https://kotlinlang.org/docs/flow.html)
- **Testing**:
  - [JUnit](https://junit.org/junit4/) for unit testing
  - [MockK](https://mockk.io/) for mocking in tests
  - [Turbine](https://github.com/cashapp/turbine) for Flow testing
  - [Compose Testing](https://developer.android.com/jetpack/compose/testing) for UI tests
  - [Robolectric](http://robolectric.org/) for Android framework testing

## 🧪 Testing Strategy

The app implements a comprehensive testing strategy:

### Unit Tests
- **ViewModels**: Test business logic, state management, and data flow
- **Repositories**: Test data handling and mapping
- **Use Cases**: Test business rules and data transformations
- **UI Models**: Test UI state representations and transformations

### UI Tests
- **Compose Tests**: Test UI components and interactions
- **Integration Tests**: Test component interactions
- **Screenshot Tests**: Visual regression testing (planned)

### Key Test Examples
1. `UiTextTest`: Unit tests for the `UiText` wrapper class
   - Tests string handling
   - Tests resource handling
   - Tests null safety
   
2. `UiTextComposeTest`: UI tests for Compose integration
   - Tests dynamic string updates
   - Tests resource string rendering
   - Tests state management
   - Tests error handling

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- JDK 17 or later
- Android SDK with minimum API level 24

### Building the App
1. Clone the repository
```bash
git clone https://github.com/yourusername/FetchTest.git
```

2. Open in Android Studio

3. Sync project with Gradle files

4. Run the app

### Running Tests
- Run unit tests:
```bash
./gradlew test
```

- Run instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## 📦 Dependencies

All dependencies are managed in the `gradle/libs.versions.toml` file for better version control and maintenance.

### Core Dependencies
```toml
[versions]
kotlin = "1.9.22"
compose = "1.5.4"
hilt = "2.48"
retrofit = "2.9.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycle" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
```

### Testing Dependencies
```toml
[libraries]
junit = { group = "junit", name = "junit", version.ref = "junit" }
mockk = { group = "io.mockk", name = "mockk", version.ref = "mockk" }
turbine = { group = "app.cash.turbine", name = "turbine", version.ref = "turbine" }
compose-ui-test = { group = "androidx.compose.ui", name = "ui-test-junit4", version.ref = "compose" }
robolectric = { group = "org.robolectric", name = "robolectric", version.ref = "robolectric" }
```

## 🤝 Contributing

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
