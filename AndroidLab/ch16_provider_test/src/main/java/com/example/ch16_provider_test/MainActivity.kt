package com.example.ch16_provider_test

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.ch16_provider_test.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val intent = Intent(Intent.ACTION_PICK,
//            ContactsContract.Contacts.CONTENT_URI   //  모든 사람
////            ContactsContract.CommonDataKinds.Email.CONTENT_URI   //   이메일 정보가 있는 사람
////            ContactsContract.CommonDataKinds.Phone.CONTENT_URI  //  전화번호가 있는 사람
//        )
//        startActivityForResult(intent, 20)

//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        intent.type = "image/*"
//        startActivityForResult(intent, 10)

        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                //inSampleSize 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    it!!.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                //이미지 로딩
                var inputStream = contentResolver.openInputStream(it!!.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("Han", "bitmap null.............")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startForResult.launch(intent)
    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            try {
                //inSampleSize 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio

                //이미지 로딩
                var inputStream = contentResolver.openInputStream(data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    binding.userImageView.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("Han", "bitmap null.............")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 20 && resultCode == Activity.RESULT_OK) {
//            val cursor = contentResolver.query(
//                data?.data!!,
//                arrayOf(
//                    ContactsContract.Contacts._ID,
//                    ContactsContract.Contacts.LOOKUP_KEY,
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
//                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
//                    else
//                        ContactsContract.Contacts.DISPLAY_NAME
//                ),
//                null,
//                null,
//                null
//            )
////            val result: String? = data?.dataString
//            Log.e("Han", "data : ${data.dataString}")
//            Log.e("Han", "cursor size : ${cursor?.count}")
//            while (cursor!!.moveToNext()){
//                val name = cursor.getString(0)
//                val phone = cursor?.getString(0)
//                Log.e("Han", "${name} - ${phone}")
//
//            }
//        }
//    }
}