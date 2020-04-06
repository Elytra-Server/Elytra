package io.elytra.api.io.i18n

import org.jetbrains.annotations.PropertyKey

open class LocatedException(
    @PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) key: String,
    val sendToPlayer: Boolean = true,
    val log: Boolean = false,
    dsl: MessageBuilder.() -> Unit = {}
) : RuntimeException(key) {
    val builder: MessageBuilder by lazy {
        MessageBuilder(
            key
        ).apply(dsl)
    }
}
