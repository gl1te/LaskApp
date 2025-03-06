package com.newsapp.lask.presentation.onboarding

sealed class OnBoardingEvent {
    object SaveAppEntry : OnBoardingEvent()
}