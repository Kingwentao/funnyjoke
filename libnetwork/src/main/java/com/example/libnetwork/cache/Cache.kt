package com.example.libnetwork.cache

import java.io.Serializable

/**
 * author: created by wentaoKing
 * date: created in 2020-03-17
 * description: 缓存的java bean
 */
data class Cache(val key: String, val data: ByteArray)