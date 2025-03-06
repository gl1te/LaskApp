package com.newsapp.lask.domain.usecases.app_entry_usecases

import com.newsapp.lask.domain.manager.LocalUserManager
import javax.inject.Inject

class SaveAppEntryUseCase @Inject constructor(
    private val userManager: LocalUserManager
) {

    suspend operator fun invoke() {
        userManager.saveAppEntry()
    }

}