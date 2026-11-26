# OLD GAMES HUB

An offline-first Android home for classic and retro-inspired games.

## Project status

**Phase 1 — Android foundation and navigation: COMPLETE**

- Kotlin + Jetpack Compose with Material 3
- Android application ID: `com.oldgameshub.app`
- Minimum Android version: Android 8.0 (API 26)
- Compile / target SDK: 35
- JDK 17, Android Gradle Plugin 8.7.3, Kotlin 2.0.21
- Approved light theme: Warm Ivory, Royal Blue, Deep Navy and Coral
- Bengali and English UI copy
- Edge-to-edge support with keyboard resize handling
- Type-safe Navigation Compose routes
- Home screen with links to the Library and game details
- Game Library with search, category filters and sorting
- Game Details screen with game description, planned controls and honest play status
- GitHub Actions debug build completed successfully for the current main commit

The current catalog contains eight retro-inspired game concepts. They are information-only entries: playable game engines have not been implemented yet. The Play action clearly reports that gameplay is coming later.

The My Games and Profile tabs are navigation placeholders and are part of the next implementation phase. Favorites persistence, game history, local multiplayer, cloud features, physical-device testing and release APK work have not been implemented yet.

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

The GitHub Actions workflow runs the debug build. The current main commit has a successful workflow run; a physical-device install and gameplay test have not yet been performed.

## Development principles

- Keep the app offline-first; no mandatory account or network connection.
- Preserve the approved light visual direction.
- Keep the app lightweight for entry-level Android devices.
- Add only original, user-provided, or properly licensed game assets and content.
- Do not bundle copyrighted ROMs or game files without permission.
- Make small, reviewable changes and verify builds before claiming they pass.

## Roadmap

1. **Complete:** Android foundation, Home, Library, Details and navigation
2. **Next:** Complete My Games and Profile with local persistence
3. First original playable retro games
4. Local save data, favorites and player stats
5. Local Wi-Fi multiplayer exploration
6. Performance and device testing on low-memory phones
7. Release signing and APK distribution
