package io.elytra.api.events

/**
 * Determines an event has being able to be cancelled
 */
interface Cancellable {
    var cancelled: Boolean
}
