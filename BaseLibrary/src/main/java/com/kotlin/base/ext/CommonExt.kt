package com.kotlin.base.ext

import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.widgets.DefaultTextWatcher
import com.kotlin.base.widgets.GridViewItemDecoration
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find


//Kotlin通用扩展


/*
    扩展Observable执行
 */
fun <T> Observable<T>.execute(
    subscriber: BaseSubscriber<T>,
    lifecycleProvider: LifecycleProvider<*>
) {
    this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(lifecycleProvider.bindToLifecycle())
        .subscribe(subscriber)

}

/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert(): Observable<T> {
    return this.flatMap(BaseFunc())
}

/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean(): Observable<Boolean> {
    return this.flatMap(BaseFuncBoolean())
}

/*
    扩展点击事件
 */
fun View.onClick(listener: View.OnClickListener): View {
    setOnClickListener(listener)
    return this
}

/*
    扩展点击事件，参数为方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method() }
    return this
}

/*
    扩展Button可用性
 */
fun View.enable(et: EditText, method: () -> Boolean) {
    val btn = this
    et.addTextChangedListener(object : DefaultTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            btn.isEnabled = method()
        }
    })
}

/*
    ImageView加载网络图片
 */
fun ImageView.loadUrl(url: String?,placeholder:Int) {
    GlideUtils.loadUrlImage(context, url, placeholder,this)
}

/*
    多状态视图开始加载
 */
fun MultiStateView.startLoading() {
    viewState = MultiStateView.VIEW_STATE_LOADING
    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
    (animBackground as AnimationDrawable).start()
}

/*
    扩展视图可见性
 */
fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

//隐藏虚拟键盘
fun Context.hideKeyboard(v: View) {
    var imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (imm.isActive) {
        imm.hideSoftInputFromWindow(v.applicationWindowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

/**
 * @desc 填充器
 */
fun initInflater(context: Context, layoutRes: Int, parent: ViewGroup): View {
    return LayoutInflater.from(context).inflate(layoutRes, parent, false)
}

fun callPhone(context: Context, phoneNum: String) {
    var intent = Intent(Intent.ACTION_DIAL);
    var data = Uri.parse("tel:${phoneNum}");
    intent.setData(data);
    context.startActivity(intent);
}

/**
 * 获取首字符
 */
fun getFirstChar(str: String?): String {
    if (TextUtils.isEmpty(str)) {
        return ""
    }
    return str?.subSequence(0, 1).toString()
}

/**
 * 字体加粗
 */
fun TextView.isFakeBoldText(isFakeBoldText: Boolean) {
    paint.isFakeBoldText = isFakeBoldText
}

fun RecyclerView.vertical(column: Int, space: Int) {
    layoutManager = GridLayoutManager(context, column)
    var itemDecorationCount = itemDecorationCount
    if (itemDecorationCount <= 0) {
        addItemDecoration(
            GridViewItemDecoration(
                column,
                space
            )
        )
    }
}