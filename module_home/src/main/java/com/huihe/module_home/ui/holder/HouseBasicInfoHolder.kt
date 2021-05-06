package com.huihe.module_home.ui.holder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import com.huihe.module_home.R

class HouseBasicInfoHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    var tvTitle: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailTitle)
    var tvPrice: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailPrice)
    var tvArgePrice: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailArgePrice)
    var tvFloorage: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailFloorageValue)
    var tvHShape: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailHShapeValue)
    var tags: TagContainerLayout =
        itemView.findViewById<TagContainerLayout>(R.id.houseDetailTags)
    var tvTel: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailTel)
    var tvFollow: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailFollow)
    var tvTakeLook: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailTakeLook)
    var tvLog: TextView = itemView.findViewById<TextView>(R.id.tvHouseDetailLog)
    var tvOwnerName: TextView =
        itemView.findViewById<TextView>(R.id.tvHouseDetailOwnerNameValue)
}