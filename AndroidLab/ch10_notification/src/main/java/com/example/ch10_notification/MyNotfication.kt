package com.example.ch10_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.example.ch10_notification.databinding.ActivityMyNotficationBinding

class MyNotfication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMyNotficationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationButton.setOnClickListener {
            val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val builder: NotificationCompat.Builder
            //  26버전 이상
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val channelId = "one-channel"
                val channelName = "JaeHyeon`s Channel One"
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    //  채널 설명
                    description = "JH`s Channel One Description"
                    //  홈 화면 뱃지 아이콘 출력 여부 - 미확인 알림갯수 표시
                    setShowBadge(true)
                    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)
                    //  진동 울림 여부
                    enableVibration(true)
//                    //  진동 패턴
//                    vibrationPattern = longArrayOf(100, 200, 300)
//                    //  불빛 표시 여부
//                    enableLights(true)
//                    //  불빛 색상
//                    lightColor = Color.RED
                }
                //  채널을 NotificationManager에 등록
                manager.createNotificationChannel(channel)
                //  채널을 이용하여 builder 생성
                builder = NotificationCompat.Builder(this, channelId)

            //  26버전 이하
            } else{
                builder = NotificationCompat.Builder(this)
            }

            builder.run {
                //  알림의 기본 정보
                setSmallIcon(R.drawable.small)
                setWhen(System.currentTimeMillis())
                setContentTitle("한재현")
                setContentText("ㅎㅇㅎㅇ")
                setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big))
            }
            //  원격 입력 설정
            //  RemoteInput의 입력을 식별하는 값
            val KEY_TEXT_REPLY = "key_text_reply"
            //  입력 힌트
            var replyLabel: String = "답장"
            var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
                setLabel(replyLabel)
                build()
            }
            val replyIntent = Intent(this, ReplyReceiver::class.java)
            val replyPendingIntent = PendingIntent.getBroadcast(
                this, 30, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.addAction(
                NotificationCompat.Action.Builder(
                    R.drawable.send,
                    "답장",
                    replyPendingIntent
                ).addRemoteInput(remoteInput).build()
            )
            manager.notify(11, builder.build())
        }
    }
}