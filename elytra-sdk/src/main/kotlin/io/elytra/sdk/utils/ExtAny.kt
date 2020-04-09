package io.elytra.sdk.utils

fun <T> T.asJson(): String = ElytraConsts.GSON.toJson(this)
inline fun <reified T> fromJson(path: String): T = ElytraConsts.GSON.fromJson(path, T::class.java)
