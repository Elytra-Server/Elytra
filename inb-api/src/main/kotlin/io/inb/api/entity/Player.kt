package io.inb.api.entity

import io.inb.api.network.Session

data class Player(val session: Session, val username: String)
