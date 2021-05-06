package com.huihe.module_home.ui.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R

class ItemPhotoHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView){
    var mRvHousePhoto = itemView.findViewById<RecyclerView>(R.id.house_detail_rvHousePhoto)
}