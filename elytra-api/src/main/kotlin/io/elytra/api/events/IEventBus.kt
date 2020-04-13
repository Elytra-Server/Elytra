package io.elytra.api.events

import io.reactivex.rxjava3.core.Observable

/**
 * Represents the elytra event bus using rxJava as a middleman
 */
interface IEventBus {

    fun listen(): Observable<Any>

    fun post(event: ElytraEvent)
}
