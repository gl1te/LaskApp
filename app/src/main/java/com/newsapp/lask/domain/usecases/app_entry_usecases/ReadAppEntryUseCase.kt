package com.newsapp.lask.domain.usecases.app_entry_usecases

import com.newsapp.lask.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntryUseCase @Inject constructor(
    private val userManager: LocalUserManager
) {

    operator fun invoke(): Flow<Boolean>{
        return userManager.readAppEntry()
    }

}