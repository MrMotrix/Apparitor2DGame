public enum HitboxState {
    ACTIVE,    // Standard collision
    DISABLED,  // No collision
    TRIGGER,   // Used for detecting events, but doesn't block movement
    GHOST      // Can pass through objects but still detects collisions
}
