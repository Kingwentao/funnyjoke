package com.example.libnetwork.cache

import androidx.room.*


/**
 * author: created by wentaoKing
 * date: created in 2020-03-17
 * description: 缓存的java bean
 */

@Entity(
    tableName = "cache"

    //外键,一般用于多表数据查询.可以配置多个外键
    //ForeignKey用来设置关联表数据更新时所进行的操作，
    // 比如可以在@ForeignKey注解中设置onDelete=CASCADE，这样当Cache表中某个对应记录被删除时，ForeignTable表的所有相关记录也会被删除掉。
/*    , foreignKeys = [

        ForeignKey(

            entity = CacheDatabase::class,
            //parentColumns:与之关联表ForeignTable表中的列名
            parentColumns = arrayOf("id"),
            //childColumns:本表的列的名称,必须要和parentColumns个数一致。
            childColumns = arrayOf("key"),
            //onDelete:关联表中某条记录被delete或update时,本表应该怎么做：
            //         NO_ACTION:什么也不做,
            //         RESTRICT:本表跟parentColumns有关系的数据会立刻删除或更新,但不允许一对多的关系,
            //         SET_NULL:本表所跟parentColumns有关系的数据被设置为null值，
            //         SET_DEFAULT:本表所有跟parentColumns有关系的数据被设置为默认值,也是null值
            //         CASCADE:本表所有跟parentColumns有关系的数据一同被删除或更新
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.SET_DEFAULT
        )]*/
)
data class Cache(
    @PrimaryKey
    val key: String,

    @ColumnInfo(name = "_data")
    val data: ByteArray

    //  @Embedded
    // val user: User  //user对象都会被映射到cache表里去
)

