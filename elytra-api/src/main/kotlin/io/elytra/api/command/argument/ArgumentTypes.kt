package io.elytra.api.command.argument

class ArgumentTypes {

	companion object {

		val STRING = object : ArgumentType<String?> {
			override fun parse(values: List<String>, indexNumber: Int): String? = values[indexNumber]
		}

		val INT = object : ArgumentType<Int?> {
			override fun parse(values: List<String>, indexNumber: Int): Int? = values[indexNumber].toIntOrNull()
		}

		val JOINED_STRINGS = object : ArgumentType<String?> {
			override fun parse(values: List<String>, indexNumber: Int): String? {
				val stringBuilder: StringBuilder = StringBuilder()
				for (valueIndex in indexNumber until values.size) {
					stringBuilder.append(values[valueIndex])
				}
				return stringBuilder.toString()
			}
		}
	}

}
