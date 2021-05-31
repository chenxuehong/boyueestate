package com.kotlin.base.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.kotlin.base.R;
import com.kotlin.base.utils.GlideUtils;
import com.kotlin.base.utils.PhotoTools;
import com.kotlin.base.utils.PhotoWindowUtils;

import java.util.List;


public class GalleryPagerAdapter extends PagerAdapter {
    private Context context;
    private List<String> urls;
    private AppCompatActivity activity;
    public GalleryPagerAdapter(Context context, List<String> urls, AppCompatActivity activity) {
        this.context = context;
        this.urls = urls;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return urls != null ? urls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        PhotoView p = new PhotoView(context);
        p.setScaleType(ImageView.ScaleType.FIT_CENTER);
        p.enable();
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        p.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showSaveImgDialog(context, urls.get(position));
                return false;
            }
        });
        Glide.with(context).load(urls.get(position))
                .placeholder(R.drawable.is_empty)
                .error(R.drawable.is_empty)
                .into((ImageView) p);
        container.addView(p);
        return p;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private void showSaveImgDialog(final Context context, final String url) {
        View rootView = activity.getWindow().getDecorView().getRootView();
        View containerView = View.inflate(context,R.layout.layout_save_gallery,null);
        final  CustomPopWindow customPopWindow = PhotoWindowUtils.popBottomDialog(rootView, context, containerView);
        containerView.findViewById(R.id.layout_save_gallery_tv_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow !=null){
                    customPopWindow.dissmiss();
                }
                PhotoTools.saveBitmap(context, url);
            }
        });
        containerView.findViewById(R.id.layout_save_gallery_tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customPopWindow !=null){
                    customPopWindow.dissmiss();
                }
            }
        });
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
