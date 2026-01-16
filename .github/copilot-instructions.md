# PackieAI Android Call Screening App - AI Agent Instructions

## Architecture Overview
This is a minimal Android application that implements call screening functionality using Android's CallScreeningService API. The app consists of two main components:

- **MainActivity**: Handles runtime permissions required for call screening (READ_PHONE_STATE, ANSWER_PHONE_CALLS, CALL_PHONE)
- **PackieCallScreeningService**: Intercepts incoming calls and applies basic scam detection logic

## Key Data Flows
1. Incoming call triggers `onScreenCall()` in PackieCallScreeningService
2. Service extracts phone number from `Call.Details.getHandle()`
3. `isLikelyScam()` checks for:
   - Null/empty numbers
   - Numbers shorter than 10 digits
   - Toll-free area codes (800, 888, 877, etc.)
4. If scam detected: reject call, skip log/notification, immediately call transfer number `9472254324`
5. Otherwise: allow call to proceed normally

## Critical Developer Workflows
- **Build & Run**: Use `./gradlew installDebug` to build and install on device/emulator
- **Permissions**: App requires Android 10+ (API 29) for CallScreeningService. Grant all phone permissions in app settings
- **Testing**: Enable call screening in Phone app settings → Spam & Call Screen → "PackieAi"
- **Debugging**: Use Android Studio debugger or `adb logcat` to monitor call screening events

## Project-Specific Patterns
- **Scam Detection**: Extend `isLikelyScam()` method in PackieCallScreeningService.java for additional logic (e.g., specific number blacklists, pattern matching)
- **Transfer Number**: Currently hardcoded as `9472254324` - consider making configurable via SharedPreferences or remote config
- **Error Handling**: Service failures are silent; add logging to `onScreenCall()` for debugging
- **Permissions**: Always check permission status before assuming call screening is active

## Key Files
- `app/src/main/java/com/packieai/PackieCallScreeningService.java` - Core screening logic
- `app/src/main/java/com/packieai/MainActivity.java` - Permission management UI
- `app/src/main/AndroidManifest.xml` - Required permissions and service declaration
- `app/build.gradle` - Android config (minSdk 29, Java 8)

## Integration Points
- Relies on Android Telecom framework for call interception
- No external APIs or network calls currently
- Could integrate with remote scam databases or AI services for enhanced detection</content>
<parameter name="filePath">/Users/matt/Documents/PackieAI/PackieAIStatic/PackieAiAndroid/.github/copilot-instructions.md