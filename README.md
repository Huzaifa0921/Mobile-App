# 🌿 PlantPal – Your Pocket Plant Care Reminder

PlantPal is an Android application that helps users keep their plants healthy by providing a simple, friendly entry point for plant care tracking. This repository contains the foundation of the app: a splash screen, a validated login screen with Google Sign-In support, and a registration screen.

Built with **Java**, **XML**, and **Android Studio**.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Screens](#screens)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Firebase Setup](#firebase-setup)
- [Current Limitations](#current-limitations)
- [Roadmap](#roadmap)
- [Learning Outcomes](#learning-outcomes)
- [Author](#author)

---

## Overview

PlantPal demonstrates core Android development concepts: multiple activities, XML layout design, user input validation, click event handling, and navigation between screens using Intents. It is designed as the starting point for a full plant care reminder app.

## Features

- **Splash screen** with app name and tagline, auto-navigating to login after 3 seconds
- **Login screen** with email and password fields
- **Input validation** with Toast feedback for empty fields and invalid email format
- **Google Sign-In** button (requires Firebase configuration)
- **Forgot Password** link and **Create Account** button
- **Registration screen** (placeholder for future development)

## Screens

| Screen | Description | Files |
|---|---|---|
| Splash | Green background, app name, tagline "Never forget to water again", 3-second delay | `SplashActivity.java`, `activity_splash.xml` |
| Login | Google button, email/password fields, validation, forgot-password link | `LoginActivity.java`, `activity_login.xml` |
| Registration | Blank white screen, opened via Create Account | `RegisterActivity.java`, `activity_register.xml` |

> 📸 Add screenshots here: `docs/splash.png`, `docs/login.png`, `docs/register.png`

## Tech Stack

| Component | Technology |
|---|---|
| Language | Java |
| UI | XML (ConstraintLayout, TextView, EditText, Button) |
| IDE | Android Studio |
| Authentication | Firebase Authentication, Google Sign-In |
| Navigation | Explicit Intents |

## Project Structure

```
PlantPal/
├── app/
│   ├── google-services.json        # Not committed – add your own
│   └── src/main/
│       ├── java/com/example/plantpal/
│       │   ├── SplashActivity.java
│       │   ├── LoginActivity.java
│       │   └── RegisterActivity.java
│       ├── res/layout/
│       │   ├── activity_splash.xml
│       │   ├── activity_login.xml
│       │   └── activity_register.xml
│       └── AndroidManifest.xml
├── build.gradle
└── README.md
```

## Getting Started

### Prerequisites

- Android Studio (latest stable version)
- Android SDK installed through the SDK Manager
- An Android emulator or physical device

### Installation

```bash
git clone https://github.com/<your-username>/PlantPal-Android-App.git
```

1. Open the cloned folder in Android Studio.
2. Accept any Gradle sync or SDK update prompts.
3. Click **Run ▶** to launch the app on an emulator or device.

**Package name:** `com.example.plantpal`

## Firebase Setup

Google Sign-In will not complete until Firebase is configured:

1. Create a project in the [Firebase Console](https://console.firebase.google.com/).
2. Register an Android app with the package name `com.example.plantpal`.
3. Download `google-services.json` and place it in the `app/` folder.
4. Enable **Google** as a sign-in provider under **Authentication → Sign-in method**.
5. Add your debug SHA-1 fingerprint in Project Settings.

To get your SHA-1 fingerprint:

```bash
./gradlew signingReport
```

> ⚠️ Do not commit `google-services.json` to a public repository. Add it to `.gitignore`.

## Current Limitations

- The email/password **Log In** button only validates input; it is not connected to an authentication backend.
- The **Registration** screen is a blank placeholder.
- Google Sign-In requires the Firebase setup above.
- This project has not been verified on a full Gradle build in every environment. Resolve any SDK or version prompts in Android Studio on first open.

## Roadmap

- [ ] Connect email/password login to Firebase Authentication
- [ ] Build the registration form
- [ ] Add a plant list with watering schedules
- [ ] Add push notification reminders
- [ ] Add plant photos and care notes

## Learning Outcomes

This project demonstrates:

- Android Activities and the activity lifecycle
- XML layout design with ConstraintLayout
- Input validation and user feedback with Toasts
- Button click listeners
- Screen navigation with Intents
- Introductory Firebase Authentication integration



---

*Built as a mobile application development project.*
