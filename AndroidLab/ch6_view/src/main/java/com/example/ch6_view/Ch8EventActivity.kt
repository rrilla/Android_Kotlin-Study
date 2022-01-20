package com.example.ch6_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.CompoundButton
import com.example.ch6_view.databinding.ActivityCh8EventBinding
import com.example.ch6_view.databinding.ActivityMainBinding

class Ch8EventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCh8EventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBox.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
                Log.e("Han", "1 - ${p0.toString()} / ${p1.toString()}")
            }
        })

//        binding.checkBox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
//            compoundButton, b ->
//            Log.e("Han", "2 - ${compoundButton.toString()} / ${b.toString()}")
//        })

//        binding.checkBox.setOnCheckedChangeListener {
//            compoundButton, b ->
//            Log.e("Han", "3 - ${compoundButton.toString()} / ${b.toString()}")
//        }

        binding.checkBox.setOnCheckedChangeListener{
            c,b ->
            Log.e("han","${c.toString() + b.toString()}");
        }
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