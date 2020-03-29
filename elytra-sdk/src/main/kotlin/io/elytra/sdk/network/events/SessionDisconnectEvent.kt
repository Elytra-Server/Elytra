package io.elytra.sdk.network.events

import io.elytra.api.events.ElytraEvent


class SessionDisconnectEvent(val sessionId: String) : ElytraEvent
