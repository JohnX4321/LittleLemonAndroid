package com.example.littlelemon.vm

import androidx.lifecycle.ViewModel
import com.example.littlelemon.models.SharedPrefsRepo
import com.example.littlelemon.models.UserData
import com.example.littlelemon.ui.events.OnboardingEvent
import com.example.littlelemon.utils.Const
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefs: SharedPrefsRepo
) : ViewModel() {

    private val _state = MutableStateFlow(UserData())
    val state: Flow<UserData> = _state

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.OnFirstNameChanged -> _state.value = _state.value.copy(firstname = event.name)
            is OnboardingEvent.OnLastNameChanged -> _state.value = _state.value.copy(lastName = event.name)
            is OnboardingEvent.OnEmailChanged -> _state.value = _state.value.copy(email = event.email)
            is OnboardingEvent.OnSabeUserData -> saveLoginData(_state.value)
        }
    }

    fun isInputDataValid() = _state.value.firstname.isNotBlank()

    private fun saveLoginData(
        data: UserData
    ) {
        prefs.putString(Const.FN_KEY,data.firstname)
        prefs.putString(Const.LN_KEY,data.lastName)
        prefs.putString(Const.EM_KEY,data.email)
    }

    fun isUserLoggedIn() = prefs.contains(Const.FN_KEY) && prefs.contains(Const.LN_KEY) && prefs.contains(Const.EM_KEY)

}