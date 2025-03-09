package com.newsapp.lask.domain.usecases.app_login_usecases

import com.newsapp.lask.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppLoginUseCase @Inject constructor(
    private val localUserManager: LocalUserManager,
) {

    suspend operator fun invoke() {
        localUserManager.saveLoginAppEntry()
    }

}