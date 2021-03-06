package com.example.ch20_firebase.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ch20_firebase.MyApplication
import com.example.ch20_firebase.databinding.ItemMainBinding
import com.example.ch20_firebase.model.ItemData


class MyViewHolder(val binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val itemList: MutableList<ItemData>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemMainBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            itemEmailView.text=data.email
            itemDateView.text=data.date
            itemContentView.text=data.content
        }

        //스토리지 이미지 다운로드........................
        val imgRef= MyApplication.storage
            .reference
            .child("images/${data.docId}.jpg")

        //  firebase-ui-storage 라이브러리 미사용
//        imgRef.downloadUrl.addOnCompleteListener { task ->
//            Log.e("Han", "${task.result}")
//            if (task.isSuccessful) {
//                Glide.with(context )
//                    .load(task.result)
//                    .into(holder.binding.itemImageView)
//            }
//        }.addOnSuccessListener {
//            Log.e("Han", "$it")
//        }

        //  firebase-ui-storage 라이브러리 이용하여 load()에 StorageReference 직접 전달
        Glide.with(context )
            .load(imgRef)
            .into(holder.binding.itemImageView)
    }
}

