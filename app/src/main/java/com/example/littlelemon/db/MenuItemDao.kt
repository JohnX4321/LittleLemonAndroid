package com.example.littlelemon.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {

    @Query("SELECT * FROM menu_items")
    fun getAll(): Flow<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg menuItems: MenuItemEntity)

    @Query("DELETE FROM menu_items")
    suspend fun deleteAll()

    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    fun isEmpty(): Boolean

}