package io.elytra.api.command.argument

class ArgumentTypes {

    class Default : ArgumentType<String?> {
        override fun parse(stringArgumentList: List<String>, index: Int): String? = stringArgumentList[index]
    }

    class Integer : ArgumentType<Int?> {
        override fun parse(stringArgumentList: List<String>, index: Int): Int? = stringArgumentList[index].toIntOrNull()
    }

    class JoinedString : ArgumentType<String?> {
        override fun parse(stringArgumentList: List<String>, index: Int): String? {
            val stringBuilder: StringBuilder = StringBuilder()
            for (valueIndex in index until stringArgumentList.size) {
                stringBuilder.append(stringArgumentList[valueIndex])
            }
            return stringBuilder.toString()
        }
    }
}
