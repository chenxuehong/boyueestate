package com.huihe.module_home.ui.adapter;

import android.content.Context;

import com.huihe.boyueentities.protocol.home.HouseDetail;
import com.kotlin.base.utils.GlideUtils;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

public class ImageAdapter extends BannerImageAdapter<HouseDetail.ImagUrlsBean> {
    private Context context;
    public ImageAdapter(Context context,List<HouseDetail.ImagUrlsBean> datas) {
        super(datas);
        this.context = context;
    }

    @Override
    public void onBindView(BannerImageHolder holder, HouseDetail.ImagUrlsBean data, int position, int size) {
        GlideUtils.INSTANCE.loadImage(context,data.getUrl(),holder.imageView);

    }
}
