package io.elytra.api.utils

import org.koin.core.context.KoinContextHandler
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

/**
 * Get instance instance from Koin
 * @param qualifier
 * @param parameters
 */
inline fun <reified T> get(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T = KoinContextHandler.get().get(qualifier, parameters)

/**
 * Lazy inject instance from Koin
 * @param qualifier
 * @param parameters
 */
inline fun <reified T> inject(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): Lazy<T> = kotlin.lazy(kotlin.LazyThreadSafetyMode.NONE) { KoinContextHandler.get().get<T>(qualifier, parameters) }

/**
 * Get instance instance from Koin by Primary Type P, as secondary type S
 * @param parameters
 */
inline fun <reified S, reified P> bind(
    noinline parameters: ParametersDefinition? = null
): S = KoinContextHandler.get().bind<S, P>(parameters)
