package com.huihe.module_home.ui.adpter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.lujun.androidtagview.TagContainerLayout
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.YuanFenConverter
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.layout_house_detail_banner_item.view.*
import kotlinx.android.synthetic.main.layout_house_rewarks_info_item.view.*

class HouseDetailRvAdapter(mContext: Context?) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext!!) {

    var banner:Banner<HouseDetail.ImagUrlsBean,ImageAdapter>?=null
    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return CustomersModule.HouseDetailType.BANNER
            }
            1 -> {
                return CustomersModule.HouseDetailType.BASIC_INFO
            }
            2 -> {
                return CustomersModule.HouseDetailType.ITEM_DETAIL_INFO
            }
            3 -> {
                return CustomersModule.HouseDetailType.ITEM_REWARKS_INFO
            }
            4 -> {
                return CustomersModule.HouseDetailType.ITEM_OWNER_INFO
            }
            5 -> {
                return CustomersModule.HouseDetailType.ITEM_PHOTO
            }
            6 -> {
                return CustomersModule.HouseDetailType.ITEM_REFERURL
            }
            else -> return CustomersModule.HouseDetailType.MAP
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        // CustomersModule.HouseDetailType.MAP
        var holder: RecyclerView.ViewHolder =
            MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
        when (viewType) {
            CustomersModule.HouseDetailType.BANNER -> holder = BannerHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_banner_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.BASIC_INFO -> holder = HouseBasicInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_basic_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> holder = ItemHouseDetailHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> holder = ItemHouseRewarksHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_rewarks_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> holder = ItemHouseOwnerInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_owner_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_PHOTO -> holder = ItemPhotoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_photo_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REFERURL -> holder = ItemPhotoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_photo_item,
                    parent
                )
            )
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemHouseDetail = dataList[position]
        when (getItemViewType(position)) {
            CustomersModule.HouseDetailType.BANNER -> {
                initBanner(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.BASIC_INFO -> {
                initBasicInfo(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> {
                initDetailInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> {
                var rewarksHolder: ItemHouseRewarksHolder = holder as ItemHouseRewarksHolder
                rewarksHolder.itemView.tvHouseDetailRewarks.text = itemHouseDetail.rewarks
            }
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> {
                initOwnerInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_PHOTO -> {

            }
            CustomersModule.HouseDetailType.ITEM_REFERURL -> {

            }
        }
    }

    private fun initDetailInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseDetailHolder = holder as ItemHouseDetailHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailRvItemAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.detailInfoList!!)
    }

    private fun initOwnerInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseOwnerInfoHolder = holder as ItemHouseOwnerInfoHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailOwnerRvAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.ownerInfoList!!)
    }

    private fun initBanner(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var bannerHolder: BannerHolder = holder as BannerHolder
        banner =
            bannerHolder.itemView.houseDetailBanner as Banner<HouseDetail.ImagUrlsBean, ImageAdapter>?
        bannerHolder.itemView.houseDetailBanner?.apply {
            adapter = ImageAdapter(mContext, itemHouseDetail.bannerList)
            indicator = CircleIndicator(mContext)
            setIndicatorSelectedColorRes(R.color.main_color)
            setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            setBannerGalleryEffect(mContext.resources.getDimensionPixelOffset(R.dimen.dp_6),
                mContext.resources.getDimensionPixelOffset(R.dimen.dp_6))
            setBannerRound(6f)
        }
    }

    private fun initBasicInfo(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var basicHolder: HouseBasicInfoHolder = holder as HouseBasicInfoHolder
        basicHolder.tvTitle.text =
            "${itemHouseDetail?.basicInfo?.villageName}-${itemHouseDetail?.basicInfo?.building}-${itemHouseDetail?.basicInfo?.hNum}"
        basicHolder.tvPrice.text = "${itemHouseDetail?.basicInfo?.price}万"
        var amount = itemHouseDetail?.basicInfo?.floorage?.toBigDecimal()
            ?.let { itemHouseDetail?.basicInfo?.price?.div(it) }
        var argePrice = YuanFenConverter.getRoundFloor(amount)
        basicHolder.tvArgePrice.text = "${argePrice}万/m²"
        basicHolder.tvFloorage.text = "${itemHouseDetail?.basicInfo?.floorage}m²"
        basicHolder.tvHShape.text = "${itemHouseDetail?.basicInfo?.hShape}"
        var split = itemHouseDetail?.basicInfo?.label?.split(";")
        basicHolder.tags.tags =
            if (split != null && split.size > 5) split?.subList(0, 4) else getNotNullData(
                split
            )
        basicHolder.tvOwnerName.text = itemHouseDetail?.basicInfo?.ownerName

        basicHolder.tvTel.onClick {

        }
        basicHolder.tvFollow.onClick {

        }
        basicHolder.tvTakeLook.onClick {

        }
        basicHolder.tvLog.onClick {

        }
    }

    private fun getNotNullData(split: List<String>?): List<String> {
        return split ?: mutableListOf()
    }

    internal inner class BannerHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class HouseBasicInfoHolder(itemView: View) :
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

    internal inner class ItemHouseDetailHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemHouseRewarksHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemHouseOwnerInfoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class ItemPhotoHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    internal inner class MapHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

   fun onStart() {
        //开始轮播
        banner?.start()
    }

    fun onStop() {
        //停止轮播
        banner?.stop()
    }

    fun onDestroy() {
        //销毁
        banner?.destroy()
    }

}