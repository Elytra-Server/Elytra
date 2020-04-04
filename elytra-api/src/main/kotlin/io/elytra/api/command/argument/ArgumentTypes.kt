package io.elytra.api.command.argument

class ArgumentTypes {

	companion object {

		val STRING = object : ArgumentType<String?> {
			override fun parse(stringArgumentList: List<String>, index: Int): String? = stringArgumentList[index]
		}

		val INT = object : ArgumentType<Int?> {
			override fun parse(stringArgumentList: List<String>, index: Int): Int? = stringArgumentList[index].toIntOrNull()
		}

		val JOINED_STRINGS = object : ArgumentType<String?> {
			override fun parse(stringArgumentList: List<String>, index: Int): String? {
				val stringBuilder: StringBuilder = StringBuilder()
				for (valueIndex in index until stringArgumentList.size) {
					stringBuilder.append(stringArgumentList[valueIndex])
				}
				return stringBuilder.toString()
			}
		}
	}

}
