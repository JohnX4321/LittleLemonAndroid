package com.example.littlelemon.utils

import com.example.littlelemon.models.MenuDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MenuApi @Inject constructor(
    private val httpClient: HttpClient
) {

    suspend fun fetchMenu() = httpClient.get(MENU_URL).body<MenuDto>()

    companion object {
        private const val MENU_URL =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    }
}
