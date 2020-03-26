package io.inb.api.io

import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Formatter
import java.util.logging.LogRecord

class LogFormatter : Formatter() {


	override fun format(record: LogRecord): String {
		return "[(${millisToDate(record.millis)}) ${record.level}] - ${record.message}"
	}

	private fun millisToDate(millis: Long) : String {
		val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
		val date = Date(millis)

		return dateFormat.format(date)
	}

}
