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
    fun getCache(key: String): Cache

    //只能传递对象昂,删除时根据Cache中的主键 来比对的
    @Delete
    fun delete(cache: Cache): Int

    //只能传递对象昂,删除时根据Cache中的主键 来比对的
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(cache: Cache): Int
}

