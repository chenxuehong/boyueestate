package com.huihe.module_home.ui.widget

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.huihe.module_home.R
import com.huihe.boyueentities.protocol.home.PriceReq
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.adapter.PriceRvAdapter
import com.kotlin.base.ext.enable
import com.kotlin.base.ext.hideKeyboard
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
class PriceResultView {

    private var minPrice = "0"
    private var title = "价格"

    private val prices =
        mutableListOf<String>(
            "不限",
            "200万以下",
            "200-300万",
            "300-400万",
            "400-500万",
            "500-800万",
            "800-1000万"
        )

    fun initPriceView(
        mContext: Context,
        mListener: ISearchResultListener?,
        view: View
    ) {
        val etMinValue = view.findViewById<EditText>(R.id.etPriceMinValue)
        val etMaxValue = view.findViewById<EditText>(R.id.etPriceMaxValue)
        val rvSearch = view.findViewById<RecyclerView>(R.id.rvPriceSearch)
        val tvSure = view.findViewById<TextView>(R.id.tvPriceSure)
        tvSure.enable(etMinValue) { view.isBtnEnable(etMinValue, etMaxValue) }
        tvSure.enable(etMaxValue) { view.isBtnEnable(etMinValue, etMaxValue) }

        tvSure.setOnClickListener {
            mContext.hideKeyboard(it)
            var priceRanges = getRealPriceRanges(
                etMinValue.text.toString().trim(),
                etMaxValue.text.toString().trim()
            )
            mListener?.onSearchResult(
                PriceReq(priceRanges[0], priceRanges[1]),
                String.format(
                    mContext.resources.getString(R.string.minprice_maxprice),
                    etMinValue.text.toString().trim(),
                    etMaxValue.text.toString().trim()
                ), CustomersModule.SearchType.PriceType
            )
        }
        rvSearch.layoutManager = LinearLayoutManager(mContext)
        var priceRvAdapter = PriceRvAdapter(mContext)
        rvSearch.adapter = priceRvAdapter
        priceRvAdapter.setData(prices)
        priceRvAdapter.setOnItemClickListener(
            object : BaseRecyclerViewAdapter.OnItemClickListener<String> {
                override fun onItemClick(view: View, item: String, position: Int) {
                    mContext.hideKeyboard(view)
                    var floorRanges = getPriceRanges(item, position, priceRvAdapter.dataList.size)
                    if (floorRanges?.size == 2) {
                        mListener?.onSearchResult(
                            PriceReq(floorRanges[0], floorRanges[1]),
                            item,
                            CustomersModule.SearchType.PriceType
                        )
                    } else {
                        mListener?.onSearchResult(
                            null,
                            title,
                            CustomersModule.SearchType.PriceType
                        )
                    }
                }

            })
    }

    /*
          判断按钮是否可用
       */
    private fun View.isBtnEnable(etMinValue: EditText, etMaxValue: EditText): Boolean {
        return etMinValue.text.isNullOrEmpty().not() &&
                etMaxValue.text.isNullOrEmpty().not()
    }

    private fun getPriceRanges(item: String, position: Int, length: Int): MutableList<String>? {
        var startPrice = minPrice
        var endPrice = "1000"
        when (position) {
            0 -> {
                return null
            }
            1 -> {
                startPrice = "0"
                endPrice = "200"
            }
            else -> {
                var split = item.split("-")
                startPrice = split[0]
                endPrice = split[1].substring(0, split[1].length - 1)
            }
        }
        return getRealPriceRanges(startPrice, endPrice)
    }

    fun getRealPriceRanges(startPrice: String, endPrice: String): MutableList<String> {
        var priceRanges = mutableListOf<String>()
        priceRanges.add(startPrice)
        priceRanges.add(endPrice)
        return priceRanges
    }
}
