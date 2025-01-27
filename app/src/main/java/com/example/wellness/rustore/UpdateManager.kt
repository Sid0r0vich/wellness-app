package com.example.wellness.rustore

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.rustore.sdk.appupdate.manager.factory.RuStoreAppUpdateManagerFactory
import ru.rustore.sdk.appupdate.model.AppUpdateInfo
import ru.rustore.sdk.appupdate.model.AppUpdateOptions
import ru.rustore.sdk.appupdate.model.AppUpdateType.Companion.IMMEDIATE
import ru.rustore.sdk.appupdate.model.InstallStatus
import ru.rustore.sdk.appupdate.model.UpdateAvailability
import javax.inject.Inject

class UpdateManager(context: Context) {
    val updateManager = RuStoreAppUpdateManagerFactory.create(context)

    fun start() {
        updateManager.getAppUpdateInfo()
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability == UpdateAvailability.UPDATE_AVAILABLE) {
                    update(appUpdateInfo)
                }
            }
            .addOnFailureListener { throwable ->
                Log.e("Update", "getAppUpdateInfo error", throwable)
            }
    }

    fun update(appUpdateInfo: AppUpdateInfo) {
        Log.d("update", "Update!")
        updateManager.registerListener { state ->
            when (state.installStatus) {
                InstallStatus.DOWNLOADED -> {
                    install(appUpdateInfo)
                }
                InstallStatus.DOWNLOADING -> {
                    val totalBytes = state.totalBytesToDownload
                    val bytesDownloaded = state.bytesDownloaded
                }
                InstallStatus.FAILED -> {
                    Log.e("Update", "Downloading error")
                }
            }
        }
    }

    fun install(appUpdateInfo: AppUpdateInfo) {
        updateManager.startUpdateFlow(
            appUpdateInfo,
            AppUpdateOptions.Builder().appUpdateType(IMMEDIATE).build()
        )
    }
}

class UpdateWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    val updateManager = UpdateManager(context)

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            return@withContext try {
                updateManager.start()
                Result.success()
            } catch (throwable: Throwable) {
                Log.e("Update", throwable.toString())
                Result.failure()
            }
        }
    }
}

interface UpdateRepository {
    fun checkAndUpdates(context: Context)
}

class WorkManagerUpdateRepository @Inject constructor(): UpdateRepository {

    override fun checkAndUpdates(context: Context) {
        WorkManager.getInstance(context)
            .beginWith(OneTimeWorkRequest.from(UpdateWorker::class.java))
            .enqueue()
    }
}