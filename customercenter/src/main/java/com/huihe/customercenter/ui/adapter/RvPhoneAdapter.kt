package layout

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huihe.customercenter.R
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.LogUtils
import kotlinx.android.synthetic.main.layout_phone_item.view.*

class RvPhoneAdapter(mContext: Context?) :
    BaseRecyclerViewAdapter<String, RvPhoneAdapter.ViewHolder>(
        mContext!!
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_phone_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var item = dataList[position]
        holder.itemView.set_owner_info_et_OwnerPhone.setText(item)
        holder.itemView.set_owner_info_et_OwnerPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setItemData(position, s.toString().trim())
                printListData()
            }
        })
    }

    private fun printListData() {
        LogUtils.i("PhoneList", "开始打印电话列表")
        dataList.forEach { item ->
            LogUtils.i("PhoneList", item)
        }
    }

    private fun setItemData(position: Int, item: String) {
        dataList?.removeAt(position)
        dataList?.add(position, item)
    }

    fun insertItem() {
        dataList?.add("")
        notifyItemChanged(dataList.size - 1)
    }

    fun deleteItem() {
        if (dataList.size <= 1) {
            return
        }
        dataList?.removeAt(dataList.size - 1)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}