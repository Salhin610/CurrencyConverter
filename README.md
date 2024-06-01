Currency Converter Android Application
This Android application allows users to convert currencies easily using a clean architecture pattern with MVVM (Model-View-ViewModel) design, leveraging Kotlin programming language. It incorporates modern Android development practices including data binding, coroutines for asynchronous programming, Hilt for dependency injection, Navigation Component for navigation, Retrofit for API communication, and GitHub Actions for continuous integration.

Features
Home Fragment: The main screen of the application where users can select the currencies they want to convert between using spinners. The "Swap" button allows users to quickly exchange the selected currencies. Two editable text fields enable users to input values for conversion.

Details Fragment: Provides users with detailed information about currency conversion rates. It displays the exchange rate between two currencies for today and the past two days. Additionally, it shows today's exchange rates with 10 common currencies.

Technologies Used
Kotlin: The primary programming language used for developing the application.

MVVM Architecture: Organizes codebase into separate layers for better maintainability and testability.

Clean Architecture: Encourages separation of concerns, making the codebase more modular and scalable.

Data Binding: Enables UI components to be binded directly to data in ViewModel.

Coroutines: Facilitates asynchronous programming, improving app's responsiveness.

Hilt: Used for dependency injection, making it easier to manage dependencies across the application.

Navigation Component: Simplifies navigation between fragments, providing a consistent and predictable user experience.

Retrofit: A type-safe HTTP client for Android used for communicating with RESTful APIs.

GitHub Actions: Automates the CI/CD pipeline, ensuring code quality and reliability.

Usage
To run the application, follow these steps:

Clone the repository to your local machine.
Open the project in Android Studio.
Build and run the application on an emulator or physical device.
Contributing
Contributions are welcome! If you'd like to contribute to the project, please follow these steps:

Fork the repository.
Create your feature branch (git checkout -b feature/YourFeature).
Commit your changes (git commit -am 'Add some feature').
Push to the branch (git push origin feature/YourFeature).
Open a pull request.

Acknowledgements
Special thanks to the following libraries and frameworks that made this project possible:

Kotlin
Android Jetpack
Retrofit
Hilt
Coroutines
Navigation Component
Data Binding
GitHub Actions
