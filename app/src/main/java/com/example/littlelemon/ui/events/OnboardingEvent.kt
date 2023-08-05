package com.example.littlelemon.ui.events

sealed class OnboardingEvent {
    data class OnFirstNameChanged(val name: String): OnboardingEvent()
    data class OnLastNameChanged(val name: String): OnboardingEvent()
    data class OnEmailChanged(val email: String): OnboardingEvent()
    object OnSabeUserData: OnboardingEvent()
}
