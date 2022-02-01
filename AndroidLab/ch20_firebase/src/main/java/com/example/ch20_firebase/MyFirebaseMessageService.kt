package com.example.ch20_firebase

import android.util.Log
import com.example.ch20_firebase.util.dateToString
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*

class MyFirebaseMessageService : FirebaseMessagingService() {
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.e("Han", "fcm token..........$p0")
        val data = mapOf(
            "token" to p0,
            "email" to MyApplication.email,
        )
        MyApplication.db.collection("users")
            .add(data)
            .addOnSuccessListener {
                // 스토리지에 데이터 저장 후 id값으로 스토리지에 이미지 업로드
                Log.e("Han", "token data save success")
            }
            .addOnFailureListener {
                Log.w("Han", "token data save error", it)
            }
    }
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        Log.e("Han", "fcm message..........${p0.notification}")
        Log.e("Han", "fcm message..........${p0.data}")
    }
}