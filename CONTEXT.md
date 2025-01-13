# Thamen

## What is Thamen?

Thamen is an Android app that helps manage and filter notifications from specific apps. Its
primary purpose is to keep certain apps running in the background by maintaining a persistent
notification, while preventing those apps from showing their own notifications that might distract
the user.

## How it Works

1. **Notification Listener Service**: The app uses Android's NotificationListenerService to
   intercept notifications from other apps.
2. **Target Apps Filtering**: It specifically monitors notifications from a predefined list of
   target apps.
3. **Background Operation**: Thamen runs as a foreground service to ensure reliable operation and
   prevent system termination.
4. **Permission Requirements**:
    - Notification Listener permission: Required to intercept notifications
    - Notification permission: Required to show the persistent notification that keeps the service
      alive

## Technical Implementation

### Core Components

1. **ThamenNotificationListenerService**
    - Main service that intercepts notifications
    - Implements Android's NotificationListenerService
    - Runs as a foreground service with a persistent notification

2. **NotificationChecker**
    - Implements the logic for filtering notifications
    - Uses rule-based system to determine which notifications to block

3. **TargetAppsProvider**
    - Manages the list of apps that Thamen monitors
    - Currently uses a static list defined in TargetAppsProviderStatic

### Architecture

- Written in Kotlin
- Uses Android's Service architecture
- Implements Observer pattern through Android's notification system
- Follows single-responsibility principle with separated components