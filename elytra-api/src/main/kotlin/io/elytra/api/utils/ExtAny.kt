package io.elytra.api.utils

import com.google.gson.Gson
import kotlin.contracts.contract

fun <T> T.asJson() = Gson().toJson(this)
inline fun <reified T> T.fromJson(path: String) = Gson().fromJson(path, T::class.java)

fun <T> T?.isValid(): Boolean {
    contract {
        returns(true) implies (this@isValid != null)
    }
    return this != null
}

fun <T> T?.isNotValid(): Boolean {
    contract {
        returns(false) implies (this@isNotValid != null)
    }
    return this == null
}
