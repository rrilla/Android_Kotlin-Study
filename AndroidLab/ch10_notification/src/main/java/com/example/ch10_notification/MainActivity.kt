package com.example.ch10_notification

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ch10_notification.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionGranted()
    }

    private fun permissionGranted(): Boolean {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            when {
//                ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    // You can use the API that requires the permission.
//                    Log.e("han", "권한관련 기능 사용")
//                    true
//                }
//                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                    val snackBar = Snackbar.make(binding.layout, R.string.suggest_permissison_grant_in_setting, Snackbar.LENGTH_INDEFINITE)
//                    snackBar.setAction("확인") {
//                        val intent = Intent()
//                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                        val uri = Uri.fromParts("package", packageName, null)
//                        intent.data = uri
//                        startActivity(intent)
//                    }
//                    snackBar.show()
//
//                    false
//                }
//                // In an educational UI, explain to the user why your app requires this
//                // permission for a specific feature to behave as expected. In this UI,
//                // include a "cancel" or "no thanks" button that allows the user to
//                // continue using your app without granting the permission.
//                else -> {
//                    // You can directly ask for the permission.
//                    ActivityCompat.requestPermissions(this,
//                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                        100)
//                    false
//                }
//            }
//        } else {
//            TODO("VERSION.SDK_INT < M")
//        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                val snackBar = Snackbar.make(
                    binding.layout,
                    R.string.suggest_permissison_grant,
                    Snackbar.LENGTH_INDEFINITE
                )
                snackBar.setAction("권한승인") {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        100
                    )
                }
                snackBar.show()
            } else {
                val snackBar = Snackbar.make(
                    binding.layout,
                    R.string.suggest_permissison_grant_in_setting,
                    Snackbar.LENGTH_INDEFINITE
                )
                    snackBar.setAction("확인") {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                    snackBar.show()
            }
        }
        return true
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