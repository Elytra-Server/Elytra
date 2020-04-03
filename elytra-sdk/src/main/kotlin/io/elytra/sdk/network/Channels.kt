package io.elytra.sdk.network

import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel

object Channels {

    private val EPOLL_AVAILABLE = Epoll.isAvailable()

    fun pickBestChannel(): Class<out ServerSocketChannel> {
        if (EPOLL_AVAILABLE) {
            return EpollServerSocketChannel::class.java
        }

        return NioServerSocketChannel::class.java
    }

    fun pickBestEventLoopGroup(): EventLoopGroup {
        if (EPOLL_AVAILABLE) {
            return EpollEventLoopGroup()
        }

        return NioEventLoopGroup()
    }
}
