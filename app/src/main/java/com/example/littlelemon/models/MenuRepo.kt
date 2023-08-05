package com.example.littlelemon.models

import androidx.room.withTransaction
import com.example.littlelemon.db.MenuDatabase
import com.example.littlelemon.db.toMenuItem
import com.example.littlelemon.utils.MenuApi
import com.example.littlelemon.utils.Resource
import com.example.littlelemon.utils.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface MenuRepo {
    fun getMenuItems(): Flow<Resource<List<MenuItem>>>
}

class MenuRepoImpl @Inject constructor(
    private val menuApi: MenuApi,
    private val db: MenuDatabase
) : MenuRepo {
    private val dao = db.menuItemDao

    override fun getMenuItems(): Flow<Resource<List<MenuItem>>> = networkBoundResource(
        query = {dao.getAll().map{it.map{ i-> i.toMenuItem()}}},
        fetch = {
            delay(1000L)
            menuApi.fetchMenu()
        },
        saveFetchedResult = {m->
            db.withTransaction {
                dao.deleteAll()
                dao.insertAll(*(m.menu.map {
                    it.toMenuItemEntity()
                }.toTypedArray()))
            }
        }
    )
}