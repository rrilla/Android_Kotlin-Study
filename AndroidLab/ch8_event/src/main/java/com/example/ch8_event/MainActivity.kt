package com.example.ch8_event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.KeyEvent
import android.widget.Toast
import com.example.ch8_event.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //이전 back button 을 누른 시간 저장 property
    var initTime = 0L

    //멈춤 시간 저장
    var pauseTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            binding.chronometer.base= SystemClock.elapsedRealtime() + pauseTime
            binding.chronometer.start()
            //button 의 enable/disable 조정
            binding.stopButton.isEnabled=true
            binding.resetButton.isEnabled=true
            binding.startButton.isEnabled=false
        }
        binding.stopButton.setOnClickListener {
            pauseTime = binding.chronometer.base - SystemClock.elapsedRealtime();
            binding.chronometer.stop()
            binding.stopButton.isEnabled=false
            binding.resetButton.isEnabled=true
            binding.startButton.isEnabled=true
        }
        binding.resetButton.setOnClickListener {
            pauseTime=0L
            binding.chronometer.base=SystemClock.elapsedRealtime()
            binding.chronometer.stop()
            binding.stopButton.isEnabled=false
            binding.resetButton.isEnabled=false
            binding.startButton.isEnabled=true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //뒤로가기 누를시
        if (keyCode === KeyEvent.KEYCODE_BACK) {
            //처음 뒤로가기 누르거나 누른지 3초가 지나면
            if (System.currentTimeMillis() - initTime > 3000) {
                Toast.makeText(this, "종료하려면 한번더 누르세요!!", Toast.LENGTH_SHORT).show()
                initTime = System.currentTimeMillis()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
