package io.elytra.api.events

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object EventBus : IEventBus {

    private val publisher: PublishSubject<Any> = PublishSubject.create()

    override fun listen(): Observable<Any> = publisher

    override fun post(event: ElytraEvent) = publisher.onNext(event)
}

inline fun <reified T : ElytraEvent> EventBus.listen(): Observable<T> = listen().ofType(T::class.java)
