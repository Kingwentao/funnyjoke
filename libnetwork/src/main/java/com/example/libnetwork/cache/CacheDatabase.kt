package com.example.libnetwork.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.libcommon.AppGlobals

/**
 * author: created by wentaoKing
 * date: created in 2020-03-17
 * description: 数据库缓存类
 */

@Database(entities = [Cache::class], version = 1, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {

    companion object {

        private val mDataBase: CacheDatabase

        init {

            //添加升级的版本
            val sMigration = object : Migration(1, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    //可以对数据库进行操作
                    database.execSQL("alter table teacher rename to student")
                }
            }

            //此方法只会把数据存于内存中，也就是进程被杀后，数据随之丢失
            //Room.inMemoryDatabaseBuilder()
            mDataBase = Room.databaseBuilder(
                AppGlobals.getApplication(),
                CacheDatabase::class.java,
                "funnyjoke")
                .allowMainThreadQueries()  //允许在主线程中查询
                //   .addCallback()   //对数据库部分操作进行回调用
                //   .setJournalMode()  //room的日志模式
                .addMigrations(sMigration)
                .build()


            fun get(): CacheDatabase {
                return mDataBase
            }

        }

    }


}