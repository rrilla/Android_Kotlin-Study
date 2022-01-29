package com.example.ch12_material_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch12_material_test.databinding.ActivityMainBinding
import com.example.ch12_material_test.databinding.ItemRecyclerviewBinding

//항목 View를 가지는 역활
class MyViewHolder(val binding: ItemRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)
//항목 구성자. Adapter
class MyAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    //항목 갯수를 판단하기 위해서 자동 호출
    override fun getItemCount(): Int{
        return datas.size
    }
    //항목의 뷰를 가지는 Holder 를 준비하기 위해 자동 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    //각 항목을 구성하기 위해서 호출
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding=(holder as MyViewHolder).binding
        //뷰에 데이터 출력
        binding.itemData.text= datas[position]
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리사이클러뷰를 위한 가상 데이터 준비...........
        val datas = mutableListOf<String>()
        for(i in 1..9){
            datas.add("Item $i")
        }
        //리사이클러뷰에 LayoutManager, Adapter 적용
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter=MyAdapter(datas)
    }
}