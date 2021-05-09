package com.kotlin.base.widgets

import android.content.Context
import android.text.Html
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.kotlin.base.R
import com.kotlin.base.ext.setVisible
import kotlinx.android.synthetic.main.layout_necessary_title_inputview.view.*

class NecessaryTitleInputView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var isNecessary:Boolean = false
    init {

        var obtainStyledAttributes =
            context.obtainStyledAttributes(attrs,R.styleable.NecessaryTitleView)
        var titleText =
            obtainStyledAttributes.getString(R.styleable.NecessaryTitleView_leftTitleText)
        var tipContentText =
            obtainStyledAttributes.getString(R.styleable.NecessaryTitleView_tipContentText)
        var isShowLine =
            obtainStyledAttributes.getBoolean(R.styleable.NecessaryTitleView_isShowLine, false)
        isNecessary = obtainStyledAttributes.getBoolean(
            R.styleable.NecessaryTitleView_isNecessary,
            false
        )
        var unit = obtainStyledAttributes.getString(
            R.styleable.NecessaryTitleView_unit)
        var inputType = obtainStyledAttributes.getInt(
            R.styleable.NecessaryTitleView_inputType,0x00000001)
        obtainStyledAttributes.recycle()
        initView(isNecessary, titleText, tipContentText, isShowLine,unit,inputType)

    }

    private fun initView(
        isNecessary: Boolean,
        titleText: String?,
        tipContentText: String?,
        isShowLine: Boolean,
        unit: String?,
        inputType: Int
    ) {
        View.inflate(context,R.layout.layout_necessary_title_inputview, this)
        var titleText1 = titleText
        if (isNecessary)
            titleText1 = String.format("<font color=\"#ff0000\">%s</font>%s", "*", titleText1);
        if (isNecessary) {
            tvNecessaryTitleInputviewTitle.text = Html.fromHtml(titleText1)
        } else {
            tvNecessaryTitleInputviewTitle.text = titleText1
        }
        etNecessaryTitleInputviewContent.hint = tipContentText
        vNecessaryTitleInputviewline.setVisible(isShowLine)
        tvNecessaryTitleInputviewContentUnit.text = unit
        etNecessaryTitleInputviewContent.inputType = inputType
        if (!TextUtils.isEmpty(unit)){
            tvNecessaryTitleInputviewContentUnit.setVisible(true)
        }
    }

    fun setContent(content:String?){
        if (!TextUtils.isEmpty(content)){
            etNecessaryTitleInputviewContent.setText(content)
        }
    }

    fun getContent():String?{
        return etNecessaryTitleInputviewContent.text.toString().trim()
    }

    fun getHintContent():String{
        return etNecessaryTitleInputviewContent.hint.toString().trim()
    }
}