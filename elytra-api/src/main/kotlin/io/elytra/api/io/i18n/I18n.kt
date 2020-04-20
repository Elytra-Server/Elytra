package io.elytra.api.io.i18n

import com.google.common.collect.Maps
import java.util.*
import org.jetbrains.annotations.PropertyKey

/**
 * Providers internacionalization through resource bundles
 */
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

    private val bundle: ResourceBundle = wrapBundle(ResourceBundle.getBundle(BUNDLE_BASE_NAME, locale))

    /**
     * Get the message relative to the given [key].
     * - If the key hasn't a message associated, returns the key itself.
     */
    operator fun get(@PropertyKey(resourceBundle = BUNDLE_BASE_NAME) key: String): String {
        return bundle.getString(key) ?: key
    }

    private fun wrapBundle(bundle: ResourceBundle): ResourceBundle {
        val default = if (bundle.locale == I18nLocale.DEFAULT) {
            null
        } else {
            I18n[I18nLocale.DEFAULT].bundle
        }

        return WrappedResourceBundle(bundle, default)
    }

    private inner class WrappedResourceBundle(bundle: ResourceBundle, fallback: ResourceBundle?) : ResourceBundle() {
        private val lookup: Map<String, Any>

        init {
            lookup = Maps.newHashMap()

            for (key in bundle.keys.asIterator()) {
                val value = bundle.getString(key)

                lookup[key] = parsePlaceholders(value, bundle, fallback)
            }
        }

        /**
         * Load all message placeholders to the [ŧext]
         */
        private fun parsePlaceholders(text: String, bundle: ResourceBundle, fallback: ResourceBundle?): String {
            val builder = StringBuilder()

            // The point to start searching for a match to replace
            var start = 0
            do {
                val prefixIndex = text.indexOf("{@@", start)
                if (prefixIndex == -1) {
                    break
                }
                val suffixIndex = text.indexOf('}', prefixIndex)
                if (suffixIndex == 0) {
                    break
                }
                val placeholder = text.substring(prefixIndex + 3, suffixIndex)
                // Get the replacement from the bundle
                // If not found get from the default locale bundle
                val replacement = try {
                    bundle.getString(placeholder)
                } catch (e: MissingResourceException) {
                    if (fallback != null) {
                        try {
                            fallback.getString(placeholder)
                        } catch (e: MissingResourceException) {
                            null
                        }
                    } else {
                        null
                    }
                }

                if (replacement == null) {
                    start = prefixIndex + 1
                    continue
                }

                // Add everything until the match
                for (i in start until prefixIndex) {
                    builder.append(text[i])
                }

                // Place the replacement
                builder.append(replacement)

                // Increment the next starting point
                // 2 is the text length of the color, e.g. `&a`
                start = suffixIndex + 1
            } while (start < text.length)

            for (i in start until text.length) {
                builder.append(text[i])
            }

            return builder.toString()
        }

        override fun getKeys(): Enumeration<String> {
            return object : Enumeration<String> {
                private val it = lookup.keys.iterator()
                override fun hasMoreElements(): Boolean = it.hasNext()

                override fun nextElement(): String = it.next()
            }
        }

        override fun handleGetObject(key: String): Any {
            Objects.requireNonNull(key, "Key is null")

            return lookup[key]!!
        }
    }
}
