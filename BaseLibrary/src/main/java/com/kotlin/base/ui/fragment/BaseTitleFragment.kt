package com.kotlin.base.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kotlin.base.R
import com.kotlin.base.ext.initInflater
import com.kotlin.base.widgets.HeaderBar
import kotlinx.android.synthetic.main.fragment_title.*

abstract class BaseTitleFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_title,container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTitle(titleBar)
        setFragment(getFragment())
    }

    abstract fun initTitle(titleBar: HeaderBar)

    abstract fun getFragment(): Fragment

    fun setFragment(fragment:Fragment){
        childFragmentManager.beginTransaction().replace(vsContainer.id,fragment).commit()
    }
}