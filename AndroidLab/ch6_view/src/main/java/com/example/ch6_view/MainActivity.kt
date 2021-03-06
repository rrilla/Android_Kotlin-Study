package com.example.ch6_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.CompoundButton
import com.example.ch6_view.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                Log.e("Han", "2 - ${p0.toString()} / ${p1.toString()}")
            }
        })

        binding.checkBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            Log.e("Han", "2 - ${compoundButton.toString()} / ${b.toString()}")
        })

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        Log.e("Han", "키눌맀다")
        when(keyCode){
            KeyEvent.KEYCODE_VOLUME_UP -> Log.e("Han", "불륨업 클릭")
            else -> Log.e("Han", event.toString())
        }

        return super.onKeyDown(keyCode, event)
    }
}