package com.huihe.module_home.ui.widget;

public enum SearchType {
    AreaType("1001"),
    FloorsType("1002"),
    PriceType("1003"),
    MoreType("1004"),
    SortType("1005");
    private String type;

    SearchType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
