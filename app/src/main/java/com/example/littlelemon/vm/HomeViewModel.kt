package com.example.littlelemon.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.models.HomeState
import com.example.littlelemon.models.MenuRepo
import com.example.littlelemon.ui.events.HomeEvent
import com.example.littlelemon.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    repo: MenuRepo
) : ViewModel() {
    private val menuFlow = repo.getMenuItems()
    private val searchPhrase = MutableStateFlow("")
    private val filteredCategory = MutableStateFlow("")

    val state = combine(
        menuFlow,
        searchPhrase,
        filteredCategory
    ) { menu, searchPhrase, category->
        var filteredMenu = menu.data
        val categories = menu.data?.map { it.category } ?: emptyList()

        if (searchPhrase.isNotEmpty()){
            filteredMenu = menu.data?.filter { it.title.lowercase().contains(searchPhrase.lowercase()) }
        }
        if (category.isNotEmpty()) {
            filteredMenu = menu.data?.filter { it.category.lowercase() == category.lowercase() }
        }
        return@combine HomeState(Resource.Success(filteredMenu), searchPhrase, categories.toSet(), category )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),HomeState())

    fun onEvent(event: HomeEvent) {
        when (event){
            is HomeEvent.onSearchPhraseChanged -> {
                searchPhrase.value = event.phrase
            }
            is HomeEvent.onFilter -> {
                if (event.category == filteredCategory.value) {
                    filteredCategory.value = ""
                } else {
                    filteredCategory.value = event.category
                }
            }
        }
    }
}