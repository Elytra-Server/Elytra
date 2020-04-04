package io.elytra.sdk.network

enum class SessionState {
    HELLO,
    KEY,
    AUTHENTICATING,
    READY_TO_ACCEPT,
    DELAY_ACCEPT,
    ACCEPTED
}
