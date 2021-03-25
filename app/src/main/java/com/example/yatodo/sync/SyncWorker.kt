package com.example.yatodo.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.yatodo.api.YatodoService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val yatodoService: YatodoService
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val result = yatodoService.main()
        Log.d("SyncWorker", "result: ${result.string()}")
        return Result.success()
    }
}