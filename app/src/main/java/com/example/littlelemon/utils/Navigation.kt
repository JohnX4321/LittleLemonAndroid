package com.example.littlelemon.utils

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.screens.Home
import com.example.littlelemon.ui.screens.Onboarding
import com.example.littlelemon.ui.screens.Profile
import com.example.littlelemon.vm.OnboardingViewModel
import com.example.littlelemon.vm.ProfileViewModel

@Composable
fun NavComponent(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = if (onboardingViewModel.isUserLoggedIn()) Home.route else Onboarding.route) {
        composable(Onboarding.route) {
            Onboarding(navController,onboardingViewModel)
        }
        composable(Home.route) {
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController,profileViewModel)
        }
    }
}