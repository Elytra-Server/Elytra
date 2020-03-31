package io.elytra.sdk.io

import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.InputStream

class ElytraInputStream (stream : InputStream) : DataInputStream(stream) {
	constructor(bytes: ByteArray) : this(ByteArrayInputStream(bytes))
	constructor(bytes: ByteArray, offset: Int, length: Int) : this(ByteArrayInputStream(bytes, offset, length))
}
