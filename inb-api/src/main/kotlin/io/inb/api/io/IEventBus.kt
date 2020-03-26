package io.inb.api.io

import io.reactivex.rxjava3.core.Observable

interface IEventBus {

	fun listen() : Observable<Any>

	fun post(event: ElytraEvent)
}
