package io.elytra.api.utils

import kotlin.contracts.contract

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
