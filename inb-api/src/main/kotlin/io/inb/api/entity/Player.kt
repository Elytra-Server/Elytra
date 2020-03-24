package io.inb.api.entity

import io.inb.api.network.NetworkSession

data class Player(val networkSession: NetworkSession, val username: String)
