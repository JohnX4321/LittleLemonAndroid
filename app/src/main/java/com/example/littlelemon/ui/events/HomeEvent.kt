package com.example.littlelemon.ui.events

sealed class HomeEvent {
    data class onSearchPhraseChanged(val phrase: String): HomeEvent()
    data class onFilter(val category: String): HomeEvent()
}
