package com.example.ch10_notification

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ch10_notification.databinding.ActivityMainBinding
import com.example.ch10_notification.databinding.DialogInputBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

const val REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {

    //  바인딩 객체 전역변수로 선언
    private var mBinding: ActivityMainBinding? = null
    //  !! : 객체가 null일 때 예외 발생시킴
    //  lazy, get()없이 초기화시 null값이 먼저 대입되어 빌드시 에러
    //  private val binding get() = mBinding
    private val binding by lazy {mBinding!!}

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if(isPermissionGranted()) {
            getCurrentLocation()
        }

        useCustumDialog()
        notificationSound()
    }

    fun useCustumDialog() {
        val dialogBinding = DialogInputBinding.inflate(layoutInflater)
        AlertDialog.Builder(this).run {
            setTitle("My custum dialog")
            setView(dialogBinding.root)
//            setView(R.layout.dialog_input)
            setPositiveButton("닫기", null)
            show()
        }
    }

    fun notificationSound() {
        //  안드로이드 시스템에 등록된 소리 사용
//        val notification: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//        RingtoneManager.getRingtone(applicationContext, notification).play()

        //  리소스 파일로 등록하여 소리 사용
        MediaPlayer.create(this, R.raw.test_sound).start()
    }

    fun notificationVibrate() {
        //  manifiest 권한 필요 <uses-permission android:name="android.permission.VIBRATE" />
        //  as = 지정한 타입으로 캐스트를 하고, 캐스트 할 수 없으면 null을 반환
        val vibrator =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =  this.getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator;
            } else {
                getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //  500동안 기본 세기로 진동 울리기
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
//          // 반복해서 진동 울리기 : 0.5초간 안울림 -> 1초간 100세기로 울림 -> 2초간 300세기로 울림, 한번만 반복(0은 cancel함수 호출시까지 울림)
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(500, 1000, 2000), intArrayOf(0, 100, 300), -1))
        } else {
            vibrator.vibrate(500)
        }

        Build.VERSION_CODES.S
        Build.VERSION_CODES.O
        vibrator.vibrate(500)
    }

    //  isPermissionGranted()함수 내부에서 권한 체크를 하였으나
    //  사용자가 거부할 권한이 필요하다는 경고 무시 위해 추가
    @SuppressLint("MissingPermission")
    fun getCurrentLocation() {
        Log.e("han","1")
        fusedLocationClient.lastLocation.addOnSuccessListener {
                location: Location? ->
            Log.e("han","2")
            if (location != null) {
                binding.tvLocation.text = "위도: ${ location.latitude + Random.nextInt() % 15} / 경도: ${location.longitude + Random.nextInt() % 15}"
            } else
                binding.tvLocation.text = "위치를 알 수 없습니다."
        }
    }

    private fun isPermissionGranted(): Boolean {
        Log.e("han","실행됨");
        return when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    true
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                    val snackBar = Snackbar.make(binding.layout, R.string.suggest_permissison_grant_in_setting, Snackbar.LENGTH_INDEFINITE)
//                    snackBar.setAction("확인") {
//                        val intent = Intent()
//                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                        val uri = Uri.fromParts("package", packageName, null)
//                        intent.data = uri
//                        startActivity(intent)
//                    }
//                    snackBar.show()

                    AlertDialog.Builder(this).run {
//                        setTitle("위치권한확인")
                        setIcon(android.R.drawable.ic_dialog_alert)
                        setMessage(R.string.suggest_permissison_grant_in_setting)
                        setPositiveButton("확인"){
                            _, _ ->
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package", packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        setNegativeButton("취소", null)
                        show()
                    }
                    false

                }
                else -> {
                    Log.e("han","실행됨2");
                    ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_CODE)
                    false
                }
            }
        }

    //  퍼미션 요청 다이얼로그 닫힐 때 자동 호출
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, R.string.no_permission_msg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}