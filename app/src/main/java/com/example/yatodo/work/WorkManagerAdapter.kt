package com.example.yatodo.work

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkManagerAdapter @Inject constructor(
    @ApplicationContext context: Context
) {
    private val workManager by lazy {
        WorkManager.getInstance(context)
    }

    fun enqueueUnique(tag: String, request: OneTimeWorkRequest) {
        workManager.enqueueUniqueWork(
            tag,
            ExistingWorkPolicy.KEEP,
            request
        )
    }
}