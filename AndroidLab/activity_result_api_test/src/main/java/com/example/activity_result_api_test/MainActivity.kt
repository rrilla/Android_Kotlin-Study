package com.example.activity_result_api_test

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        registerForActivityResult(ActivityResultContracts.RequestPermission()){
//            Log.e("han", "실행됨")
//        }.launch(Manifest.permission.ACCESS_FINE_LOCATION)

        val intent = Intent(Intent.ACTION_PICK,
            ContactsContract.Contacts.CONTENT_URI   //  모든 사람
////            ContactsContract.CommonDataKinds.Email.CONTENT_URI   //   이메일 정보가 있는 사람
////            ContactsContract.CommonDataKinds.Phone.CONTENT_URI  //  전화번호가 있는 사람
        )
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            Log.e("Han", "uri - $it")
            val cursor = contentResolver.query(
                it!!.data!!.data!!,
                arrayOf(
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.LOOKUP_KEY,
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
                    else
                        ContactsContract.Contacts.DISPLAY_NAME
                ),
                null,
                null,
                null
            )
//            val result: String? = data?.dataString
            Log.e("Han", "data : ${it}")
            Log.e("Han", "cursor size : ${cursor?.count}")
            if (cursor!!.moveToFirst()){
                val name = cursor.getString(0)
                val phone = cursor.getString(1)
                Log.e("Han", "${name} - ${phone} , ${cursor.getString(2)}")
        }
        }.launch(intent)
    }

    //  권한요청 창 닫힐시 자동 호출
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.e("Han", "실행됨")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}