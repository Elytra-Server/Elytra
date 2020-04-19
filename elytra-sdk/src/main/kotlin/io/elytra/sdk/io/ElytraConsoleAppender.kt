package io.elytra.sdk.io

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.CoreConstants
import ch.qos.logback.core.OutputStreamAppender
import ch.qos.logback.core.encoder.EncoderBase
import ch.qos.logback.core.joran.spi.ConsoleTarget
import io.elytra.api.chat.ChatColor
import java.io.OutputStream
import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.fusesource.jansi.AnsiConsole

class ElytraConsoleAppender : OutputStreamAppender<ILoggingEvent>() {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS")

    init {
        setEncoder(object : EncoderBase<ILoggingEvent>() {
            override fun headerBytes(): ByteArray? {
                return null
            }

            override fun footerBytes(): ByteArray? {
                return null
            }

            override fun encode(event: ILoggingEvent): ByteArray {
                val sb = StringBuilder()

                LocalDateTime.now()
                dateTimeFormatter.formatTo(LocalDateTime.now(), sb)
                sb.append(" ")

                val level = event.level.levelInt
                sb.append(
                    when {
                        level <= Level.ALL_INT -> "&bALL"
                        level <= Level.TRACE_INT -> "&bTRACE"
                        level <= Level.DEBUG_INT -> "&3DEBUG"
                        level <= Level.INFO_INT -> "&1INFO"
                        level <= Level.WARN_INT -> "&6WARN"
                        level <= Level.ERROR_INT -> "&cERROR"
                        else -> ""
                    }
                ).append("&r")

                sb.append(" [").append(event.threadName).append("] ")
                sb.append("&3").append(event.loggerName).append("&r: ")
                sb.append(event.formattedMessage)
                sb.append("&r").append(CoreConstants.LINE_SEPARATOR)

                return ChatColor.replaceColors(sb.toString(), toAnsi = true).toByteArray()
            }
        })
    }

    override fun start() {
        outputStream = wrapWithJansi(ConsoleTarget.SystemOut.stream)

        super.start()
    }

    private fun wrapWithJansi(targetStream: OutputStream): OutputStream? {
        try {
            addInfo("Enabling JANSI AnsiPrintStream for the console.")

            return AnsiConsole.wrapSystemOut(PrintStream(targetStream))
        } catch (e: Exception) {
            addWarn("Failed to create AnsiPrintStream. Falling back on the default stream.", e)
        }
        return targetStream
    }
}
