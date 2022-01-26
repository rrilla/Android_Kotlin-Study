package com.example.ch10_notification

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionGranted()
    }

    private fun permissionGranted(): Boolean {
        //  퍼미션 허용 확인 함수
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //  퍼미션 요청 함수
            ActivityCompat.requestPermissions(this, arrayOf<String>("android.permission.ACCESS_FINE_LOCATION"), 100)
            return false
        }else{
            return true
        }
    }

//    퍼미션 요청 다이얼로그 닫힐 때 자동 호출
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("Han", "requestCode : $requestCode, permissions : ${permissions.contentToString()}, grantResults : ${Arrays.toString(grantResults)}")
    }
}