package io.inb.api.entity

import io.inb.api.network.InbSession

data class Player(val inbSession: InbSession, val username: String)
