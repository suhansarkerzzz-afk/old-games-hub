# OLD GAMES HUB

An offline-first Android home for classic and retro-inspired games.

## Project status

**Phase 1 — Android foundation and navigation: COMPLETE**

**Phase 2 — My Games, Favorites and local player profile: IMPLEMENTED**

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

Phase 2 implementation includes Room-backed local collection state, favorite toggles in Library and Details, My Games views for Favorites / Recently Played / Downloaded, a local Player Profile overview, and repository tests for favorite, play-history and downloaded-state persistence. The Downloaded view intentionally shows an empty state because the app has no real game-download pipeline. Recently Played and Continue Playing are wired to the local history model but will remain empty until playable games are implemented in a later phase; the current Play action does not pretend to launch a game.

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

The GitHub Actions workflow builds the app and Android test APKs, runs unit tests, and runs Room instrumentation tests on an Android emulator. Check the latest run on the Actions page for the final result. A physical-device install and gameplay test have not yet been performed.

## Development principles

- Keep the app offline-first; no mandatory account or network connection.
- Preserve the approved light visual direction.
- Keep the app lightweight for entry-level Android devices.
- Add only original, user-provided, or properly licensed game assets and content.
- Do not bundle copyrighted ROMs or game files without permission.
- Make small, reviewable changes and verify builds before claiming they pass.

## Roadmap

1. **Complete:** Android foundation, Home, Library, Details and navigation
2. **Implemented:** My Games, Favorites, local Room persistence and Profile (play-history population and actual game downloads depend on later playable-game/download functionality)
3. First original playable retro games
4. Local save data, favorites and player stats
5. Local Wi-Fi multiplayer exploration
6. Performance and device testing on low-memory phones
7. Release signing and APK distribution
