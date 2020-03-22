package com.example.libnetwork.cache

import androidx.room.*

/**
 * author: created by wentaoKing
 * date: created in 2020-03-20
 * description: dao注解表示可以操作数据库
 */
@Dao
interface CacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(cache: Cache): Long

    @Query("select * from cache where 'key' = :key")
    fun getCache(key: String)

    @Delete
    fun delete(cache: Cache): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache): Int
}

