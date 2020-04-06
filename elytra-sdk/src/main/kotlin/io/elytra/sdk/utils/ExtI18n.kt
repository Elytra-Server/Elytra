package io.elytra.sdk.utils

import io.elytra.api.command.CommandSender
import io.elytra.api.entity.Player
import io.elytra.api.io.i18n.I18n
import io.elytra.api.io.i18n.I18nLocale
import io.elytra.api.io.i18n.LocatedException
import io.elytra.api.io.i18n.MessageBuilder
import java.util.*
import org.jetbrains.annotations.PropertyKey

/**
 * Sends a message to the [CommandSender] using his [Locale].
 *
 * Additional information like placeholders to the
 * message, can be sent throw the DSL.
 */
fun CommandSender.localeMessage(
    @PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) key: String,
    dsl: MessageBuilder.() -> Unit = {}
): MessageBuilder {
    return localeMessage(key, this, dsl)
}

/**
 * Get the [Locale] from the [CommandSender] to be used with [I18n].
 */
// TODO: Get actual i18n locale from the player
fun CommandSender.getI18nLocale(): Locale = I18nLocale.DEFAULT

fun CommandSender.getI18nLocaleAsString(): String = getI18nLocale().toString()

// fun AccountSettings.getLocale(): Locale = I18nLocale.from(locale)

/**
 * Sends a message to the [target] using his [Locale].
 *
 * Additional information like placeholders to the
 * message, can be sent throw the DSL.
 */
fun localeMessage(
    @PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) key: String,
    target: CommandSender,
    dsl: MessageBuilder.() -> Unit = {}
): MessageBuilder {
    val builder = localeMessage(key, target.getI18nLocale(), dsl)
    builder.send(target)
    return builder
}

/**
 * Create a [MessageBuilder] for the [key] and [locale].
 *
 * Additional information like placeholders to the
 * message, can be sent throw the DSL.
 */
fun localeMessage(
    @PropertyKey(resourceBundle = I18n.BUNDLE_BASE_NAME) key: String,
    locale: Locale = I18nLocale.DEFAULT,
    dsl: MessageBuilder.() -> Unit = {}
) = MessageBuilder(key, locale).apply(dsl).build()

/**
 * Register the placeholders for the [player].
 */
fun MessageBuilder.with(player: Player) = with("player", player.displayName)

/**
 * Send the message to [target], using his [Locale].
 */
fun MessageBuilder.send(target: CommandSender): MessageBuilder {
    val message = getOrBuild(target.getI18nLocale())
    target.sendMessage(message)
    return this
}

/**
 * Handle this Exception sending the message to the supposed targets.
 */
fun LocatedException.handle(sender: CommandSender? = null) {
    if (sendToPlayer && sender != null) {
        builder.send(sender)
    }
}
