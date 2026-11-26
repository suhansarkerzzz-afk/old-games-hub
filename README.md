# OLD GAMES HUB

An offline-first Android home for classic and retro games.

## Project status

**Phase 1 — Android foundation (in progress)**

- Kotlin + Jetpack Compose
- Android application ID: `com.oldgameshub.app`
- Minimum Android version: Android 8.0 (API 26)
- Compile / target SDK: 35
- Material 3 light theme using the approved Warm Ivory, Royal Blue, Deep Navy and Coral palette
- Initial Home screen and bottom navigation
- Bengali and English UI copy

The Library, My Games and Profile tabs are currently starter placeholders. Game engines, playable titles, local multiplayer, persistence and APK release work have not been implemented yet.

## Open the project

1. Open this repository in Android Studio.
2. Use the `main` branch.
3. Open the repository root as an Android/Gradle project.
4. Allow Android Studio to sync Gradle.
5. Run the `app` configuration on an Android 8.0+ device or emulator.

Use JDK 17 and Gradle 8.9 with Android Gradle Plugin 8.7.3.

## Build from command line

After Gradle is available:

```bash
gradle assembleDebug
```

The debug APK is expected at:

```
app/build/outputs/apk/debug/app-debug.apk
```

## Development principles

- Keep the app offline-first; no mandatory account or network connection.
- Preserve the approved light visual direction.
- Keep the app lightweight for entry-level Android devices.
- Add only original, user-provided, or properly licensed game assets and content.
- Do not bundle copyrighted ROMs or game files without permission.
- Make small, reviewable changes and verify builds before claiming they pass.

## Roadmap

1. Android foundation and navigation
2. Complete Library and Game Details screens
3. First original playable retro games
4. Local save data, favorites and player stats
5. Local Wi-Fi multiplayer exploration
6. Performance testing on low-memory devices
7. Release signing and APK distribution
