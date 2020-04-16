package io.elytra.api.io.i18n

import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import org.apache.commons.lang3.StringUtils
import org.jetbrains.annotations.PropertyKey

class MessageBuilder(
    @PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) val messageKey: String,
    private val locale: Locale = I18nLocale.DEFAULT
) {
    private val placeholders = mutableMapOf<String, String>()
    private val messages = mutableMapOf<Locale, String>()

    /**
     * Register the [placeholder] to the given [value].
     */
    fun with(placeholder: String, value: String): MessageBuilder {
        placeholders["{$placeholder}"] = value
        return this
    }

    /**
     * Register the [placeholder] to the given [value].
     */
    fun with(placeholder: String, value: Any): MessageBuilder {
        return with(placeholder, value.toString())
    }

    /**
     * Register the given list of [placeholders].
     */
    fun with(vararg placeholders: Pair<String, Any>): MessageBuilder {
        placeholders.forEach { with(it.first, it.second) }
        return this
    }

    /**
     * Builds the message using the [placeholders].
     *
     * Fetches the message from [I18n] using the [locale] defined
     * and builds it with the [placeholders] defined.
     */
    fun build(locale: Locale = this.locale): MessageBuilder {
        val inputText = I18n[messageKey, locale]
        this.messages[locale] = build(inputText)

        return this
    }

    /**
     * Format the [text] using the [placeholders] registered.
     *
     * @return The formatted text
     */
    private fun build(text: String): String {
        return StringUtils.replaceEach(
            text,
            placeholders.keys.toTypedArray(),
            placeholders.values.toTypedArray()
        )
    }

    /**
     * Get the message if built already, or
     * else, build a new one.
     */
    fun getOrBuild(locale: Locale = this.locale): String {
        return messages[locale] ?: build(locale).getOrBuild(locale)
    }
}
