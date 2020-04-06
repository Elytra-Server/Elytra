package io.elytra.api.io.i18n

import java.util.*
import org.jetbrains.annotations.PropertyKey

class I18n(locale: Locale) {
    companion object {
        /** Translations file location */
        const val BUNDLE_BASE_NAME = "i18n.messages"

        /** Cached bundles */
        private val bundles = mutableMapOf<Locale, I18n>()

        init {
            // Load available locales
            I18nLocale.values.forEach {
                try {
                    loadLocale(it)
                } catch (e: MissingResourceException) {
                    println("Resource for locale $it wasn't found.")
                }
            }
        }

        /**
         * Get the [I18n] implementation for the [locale].
         * - If there's no implementation uses the [I18nLocale.DEFAULT].
         */
        operator fun get(locale: Locale): I18n {
            var i18n = bundles[locale]

            require(i18n != null)

            i18n = if (I18nLocale.values.contains(locale)) {
                loadLocale(locale)
            } else {
                bundles[I18nLocale.DEFAULT]
                        ?: throw IllegalArgumentException("No implementation found for the default locale(${I18nLocale.DEFAULT}")
            }

            return i18n!!
        }

        /**
         * Get the message relative to the [key] and [locale].
         * - If there's no implementation for the  given [locale],
         * uses the [I18nLocale.DEFAULT].
         * - If the key hasn't a message associated, returns the key itself.
         */
        operator fun get(@PropertyKey(resourceBundle = BUNDLE_BASE_NAME) key: String, locale: String): String =
            get(
                key,
                I18nLocale.from(locale)
            )

        /**
         * Get the message relative to the [key] and [locale].
         * - If there's no implementation for the  given [locale],
         * uses the [I18nLocale.DEFAULT].
         * - If the key hasn't a message associated, returns the key itself.
         */
        operator fun get(@PropertyKey(resourceBundle = BUNDLE_BASE_NAME) key: String, locale: Locale = I18nLocale.DEFAULT): String {
            return get(locale)[key]
        }

        /**
         * Gets the valid [Locale] for the given Minecraft [locale],
         * and then loads the bundle for that locale.
         *
         * @throws MissingResourceException if no resource bundle
         * for the specified locale can be found.
         */
        fun loadLocale(locale: String): I18n =
            loadLocale(
                I18nLocale.from(locale)
            )

        /**
         * Load the bundle for the given [locale].
         *
         * @throws MissingResourceException if no resource bundle
         * for the specified locale can be found.
         */
        private fun loadLocale(locale: Locale): I18n {
            val i18n = I18n(locale)
            bundles[locale] = i18n
            return i18n
        }
    }

    private val bundle: ResourceBundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale)

    /**
     * Get the message relative to the given [key].
     * - If the key hasn't a message associated, returns the key itself.
     */
    operator fun get(@PropertyKey(resourceBundle = BUNDLE_BASE_NAME) key: String): String {
        return bundle.getString(key) ?: key
    }
}
