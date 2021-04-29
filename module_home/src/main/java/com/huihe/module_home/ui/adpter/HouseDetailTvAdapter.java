package com.huihe.module_home.ui.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huihe.module_home.data.protocol.HouseDetail;
import com.huihe.module_home.ui.widget.HouseDetailViewType;
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;

public class HouseDetailTvAdapter extends BaseRecyclerViewAdapter<HouseDetail, RecyclerView.ViewHolder> {

    public HouseDetailTvAdapter(@NotNull Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    class BannerHolder extends RecyclerView.ViewHolder{

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class HouseBasicInfoHolder extends RecyclerView.ViewHolder{

        public HouseBasicInfoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ItemHouseDetailHolder extends RecyclerView.ViewHolder{

        public ItemHouseDetailHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ItemHouseOwnerInfoHolder extends RecyclerView.ViewHolder{

        public ItemHouseOwnerInfoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class ItemPhotoHolder extends RecyclerView.ViewHolder{

        public ItemPhotoHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class MapHolder extends RecyclerView.ViewHolder{

        public MapHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
