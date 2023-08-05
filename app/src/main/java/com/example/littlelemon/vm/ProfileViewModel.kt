package com.example.littlelemon.vm

import androidx.lifecycle.ViewModel
import com.example.littlelemon.models.SharedPrefsRepo
import com.example.littlelemon.models.UserData
import com.example.littlelemon.ui.events.ProfileEvents
import com.example.littlelemon.utils.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefs: SharedPrefsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(getUserData())
    val state: Flow<UserData> = _state

    fun onEvent(event: ProfileEvents) {
        when(event) {
            is ProfileEvents.deleteUserData -> deleteUserData()
        }
    }

    private fun deleteUserData() = prefs.deleteAll()

    private fun getUserData() = UserData(
        prefs.getString(Const.FN_KEY,""),
        prefs.getString(Const.LN_KEY,""),
        prefs.getString(Const.EM_KEY,"")
    )

}