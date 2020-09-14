package kr.co.tjoeun.daily10minutes_20200824.fcm

import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.logging.Handler

class MyFCMService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("새로발급된 기기 토큰", p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        Log.d("받은 메시지", p0.notification?.title.toString())
        Log.d("받은 메시지", p0.notification?.body.toString())

        //val myHandler  = android.os.Handler(Looper.getMainLooper())
        val myHandler  = android.os.Handler(Looper.getMainLooper())

        myHandler.post {
            Toast.makeText(applicationContext, p0.notification?.title.toString(), Toast.LENGTH_SHORT).show()
        }


    }
}