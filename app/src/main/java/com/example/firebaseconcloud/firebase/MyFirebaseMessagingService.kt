package com.example.firebaseconcloud.firebase

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.firebaseconcloud.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val notification = remoteMessage.notification
        if (notification != null) {
            sendNotification(notification.title, notification.body)

            val intent = Intent("FCM_MESSAGE")
            intent.putExtra("title", notification.title)
            intent.putExtra("body", notification.body)
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
        }
    }

    @SuppressLint("MissingPermission")
    private fun sendNotification(title: String?, message: String?) {
        val builder = NotificationCompat.Builder(this, "default")
            .setContentTitle(title ?: "Notificación")
            .setContentText(message ?: "Mensaje recibido")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(1001, builder.build())
        }
    }
}
