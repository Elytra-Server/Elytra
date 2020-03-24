package io.inb.api.utils.extensions

import kotlin.reflect.KClass

val <T : Any> T.kClass: KClass<T>
	get() = javaClass.kotlin
