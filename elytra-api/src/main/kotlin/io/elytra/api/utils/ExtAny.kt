package io.elytra.api.utils

import com.google.gson.Gson

fun <T> T.asJson() = Gson().toJson(this)
inline fun <reified T> T.fromJson(path: String) = Gson().fromJson(path, T::class.java)
