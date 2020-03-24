package io.inb.api.io

import io.reactivex.rxjava3.core.Observable

interface IEventBus {

	fun listen() : Observable<Any>

	fun post(event: InbEvent)
}

inline fun <reified T : InbEvent> IEventBus.listen(): Observable<T> = listen().ofType(T::class.java)
