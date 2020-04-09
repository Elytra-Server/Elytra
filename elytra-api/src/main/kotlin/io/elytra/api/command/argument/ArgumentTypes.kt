package io.elytra.api.command.argument

/**
 * Default argument types
 */
class ArgumentTypes {

    class Default : ArgumentType<String?> {
        override fun parse(inputArguments: List<String>, index: Int): String? = inputArguments[index]
    }

    class Integer : ArgumentType<Int?> {
        override fun parse(inputArguments: List<String>, index: Int): Int? = inputArguments[index].toIntOrNull()
    }

    class JoinedString : ArgumentType<String?> {
        override fun parse(inputArguments: List<String>, index: Int): String? {
            val stringBuilder: StringBuilder = StringBuilder()
            for (valueIndex in index until inputArguments.size) {
                stringBuilder.append(inputArguments[valueIndex])
            }
            return stringBuilder.toString()
        }
    }
}
