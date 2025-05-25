package igor.second.coinapp.databases


import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            for (i in 0..100000) {
                Log.i("MyTag", "Uploading $i")
            }
            return Result.success()
        }catch (e: Exception){
            return Result.failure()
        }
    }
}

/*val notification = NotificationCompat.Builder(applicationContext, "default")
            .setSmallIcon(
                androidx.loader.R.drawable.notification_bg)
            .setContentTitle("Task completed")
            .setContentText("The background task has completed successfully.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        // Perform the background task here,
        // such as displaying a notification
        NotificationManagerCompat.from(applicationContext).notify(1, notification)
        return Result.success()

        val channel =
            NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)

        val notificationWorkRequest:WorkRequest = OneTimeWorkRequest.Builder(NotificationWorker::class.java)
            .setInitialDelay(30, TimeUnit.MINUTES)
            .build()
        Log.i("WorkManager",notificationWorkRequest.toString())

        // Schedule the WorkRequest with WorkManager
        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
         */