package io.inb.api.io

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

object EventBus : IEventBus {

	private val publisher: PublishSubject<Any> = PublishSubject.create()

	override fun listen(): Observable<Any> = publisher

	override fun post(event: InbEvent) = publisher.onNext(event)
}
