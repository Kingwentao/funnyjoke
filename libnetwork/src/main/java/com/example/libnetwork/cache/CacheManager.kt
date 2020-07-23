package com.example.libnetwork.cache

import java.io.*
import java.lang.Exception

/**
 * author: created by wentaoKing
 * date: created in 2020-03-24
 * description: 缓存管理类
 */
class CacheManager {

    companion object {
        /**
         *  反序列,把二进制数据转换成java object对象
         */
        fun toObject(date: ByteArray): Any? {
            var bais: ByteArrayInputStream? = null
            var ois: ObjectInput? = null
            try {
                bais = ByteArrayInputStream(date)
                ois = ObjectInputStream(bais)
                return ois.readObject()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                bais?.close()
                ois?.close()
            }
            return null
        }

        /**
         * 序列化存储数据:需要转换成二进制
         */
        fun <T> toByteArray(body: T): ByteArray {
            var baos: ByteArrayOutputStream? = null
            var oos: ObjectOutputStream? = null
            try {
                baos = ByteArrayOutputStream()
                oos = ObjectOutputStream(baos)
                oos.writeObject(body)
                oos.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                baos?.close()
                oos?.close()
            }
            return ByteArray(0)
        }

        fun <T> delete(key: String, body: T) {
            val data = toByteArray(body)
            val cache = Cache(key, data)
            CacheDatabase.get().getCache().delete(cache)

        }

        fun <T> save(key: String, body: T) {
            val data = toByteArray(body)
            val cache = Cache(key, data)
            CacheDatabase.get().getCache().save(cache)
        }

        fun getCache(key: String): Any? {
            val cache = CacheDatabase.get().getCache().getCache(key)
            return toObject(cache.data)
        }

    }
}