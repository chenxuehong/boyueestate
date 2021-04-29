package com.huihe.module_home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.huihe.module_home.R
import com.huihe.module_home.presenter.HouseMapPresenter
import com.huihe.module_home.presenter.view.FindHouseByMapView
import com.kotlin.base.ui.fragment.BaseMvpFragment

class HouseMapFragment :BaseMvpFragment<HouseMapPresenter>(),FindHouseByMapView {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_findhousebymap, container, false)
    }

    override fun onGetHouseMapResult() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun injectComponent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}