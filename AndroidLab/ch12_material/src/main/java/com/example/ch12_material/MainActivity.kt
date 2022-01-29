package com.example.ch12_material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch12_material.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments: List<Fragment>
        init {
            fragments= listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment = fragments[position]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  툴바의 내용을 액션바에 적용
        setSupportActionBar(binding.toolbar)
        //  툴바에 드로어 메뉴 토글버튼 생성
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        //  툴바에 뒤로가기 버튼 생성
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //  뒤로가기 버튼을 네비게이션 아이콘으로 설정
        toggle.syncState()
        //  네비게이션 항목 선택 이벤트 핸들러
        binding.mainDrawerView.setNavigationItemSelectedListener {
            Log.e("Han", "네비게이션 아이템 클릭 - ${it.title}")
            true
        }

        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
        //  탭 레이아웃, 뷰 페이저2 연동 / 탭 이름 설정
        TabLayoutMediator(binding.tabs, binding.viewpager) {
            tab, position ->
            tab.text = "Tab${(position + 1)}"
        }.attach()

    }

    override fun onSupportNavigateUp(): Boolean {
        Log.e("Han", "액션바에 업(뒤로가기)버튼 클릭시 동작")
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    //  액티비티의 메뉴 구성할 때 자동 호출 - 액티비티 실행시 한번
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //  툴바의 메뉴 클릭 이벤트처리
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //  드로어 메뉴 토글 버튼도 액션바 메뉴로 취급해서 따로 처리
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}