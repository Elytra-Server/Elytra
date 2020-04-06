package io.elytra.api.io.i18n

import java.util.*

object I18nLocale {
    private val ENGLISH: Locale = Locale.ENGLISH

    /** The default/fallback language to use */
    val DEFAULT: Locale = ENGLISH

    val values: Array<Locale> = arrayOf(
        ENGLISH
    )

    /**
     * Gets the valid [Locale] for the given Minecraft [locale].
     * - If the [locale] isn't supported yet, returns the [DEFAULT] Locale.
     */
    fun from(locale: String?): Locale {
        if (locale?.isNotEmpty()!!) {
            return DEFAULT
        }

        return when (locale.toLowerCase()) {
            else -> DEFAULT
        }
    }
}
