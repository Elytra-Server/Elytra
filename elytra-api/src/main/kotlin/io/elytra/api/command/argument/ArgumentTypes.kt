package io.elytra.api.command.argument

class ArgumentTypes {

    companion object {

        val STRING = object : ArgumentType<String?> {
            override fun parse(value: String) = value
        }

        val INT = object : ArgumentType<Int?> {
            override fun parse(value: String) = value.toIntOrNull()
        }
    }
}
