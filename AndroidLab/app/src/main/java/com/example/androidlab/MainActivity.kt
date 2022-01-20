package com.example.androidlab

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.androidlab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        뷰 바인딩
        val binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.root)

        binding.test.text = "asdsdfasd"
        binding.test2.setText("zzzzzzzzzzz")

//       code로 view 생성
        val name = TextView(this).apply {
            typeface = Typeface.DEFAULT
            text = "afsdf"
        }
    }
}
