package com.example.littlelemon.models

import com.example.littlelemon.utils.Resource

data class HomeState(
    val menu: Resource<List<MenuItem>> = Resource.Loading(),
    val searchPhrase: String = "",
    val categories: Set<String> = emptySet(),
    val categorySelected: String = ""
)
