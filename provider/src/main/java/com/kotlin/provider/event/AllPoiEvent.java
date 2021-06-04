package com.kotlin.provider.event;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

public class AllPoiEvent {
    public List<PoiInfo> poiInfos;
    public int index;
    public AllPoiEvent(List<PoiInfo> poiInfos,int index) {
        this.poiInfos = poiInfos;
        this.index = index;
    }
}
