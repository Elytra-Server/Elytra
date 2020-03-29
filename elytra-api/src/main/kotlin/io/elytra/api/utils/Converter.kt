package io.elytra.api.utils

interface Converter<T> {

	fun encode(to: T)

	fun decode(from: T) : T

}
