package com.newsapp.lask.domain.usecases.app_login_usecases

import com.newsapp.lask.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppLoginUseCase @Inject constructor(
    private val localUserManager: LocalUserManager,
) {

    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readLoginAppEntry()
    }

}