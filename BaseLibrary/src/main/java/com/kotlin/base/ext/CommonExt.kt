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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.kennyc.view.MultiStateView
import com.kotlin.base.R
import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import com.kotlin.base.rx.BaseSubscriber
import com.kotlin.base.ui.activity.PhotoViewActivity
import com.kotlin.base.utils.AppPrefsUtils
import com.kotlin.base.utils.GlideUtils
import com.kotlin.base.utils.LogUtils
import com.kotlin.base.utils.amin.AnimationConstants
import com.kotlin.base.widgets.DefaultTextWatcher
import com.kotlin.base.widgets.GridViewItemDecoration
import com.trello.rxlifecycle3.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


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
fun ImageView.loadUrl(url: String?, placeholder: Int) {
    GlideUtils.loadUrlImage(context, url, placeholder, this)
}

fun ImageView.loadUrl(url: String?) {
    GlideUtils.loadUrlImage(context, url, R.color.color_666666, this)
}

fun ImageView.loadHeadUrl(url: String?) {
    GlideUtils.loadUrlImage(context, url, R.drawable.head_icon, this)
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

fun RecyclerView.vertical() {
    layoutManager = LinearLayoutManager(context)
}

fun View.viewPhoto(
    context: Context?,
    position: Int,
    photoList: List<String>
) {
    val intent =
        Intent(context, PhotoViewActivity::class.java)
    intent.putExtra(PhotoViewActivity.CURRENT_POSITION, position)
    intent.putExtra(PhotoViewActivity.URLS, Gson().toJson(photoList))

    val location = IntArray(2)
    getLocationOnScreen(location)

    val centerX = (location[0] + getMeasuredWidth() / 2) as Int
    val centery = (location[1] + getMeasuredHeight() / 2) as Int
    intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTX, centerX)
    intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_PIVOTY, centery)
    intent.putExtra(AnimationConstants.ACTIVITY_ANIMATION_ENABLE, true)
    context?.startActivity(intent)
}

fun readJsonStrFromAssets(fileName: String, context: Context): String {
    //将json数据变成字符串
    val stringBuilder = StringBuilder()
    try {

        //获取assets资源管理器
        val assetManager = context.assets
        //通过管理器打开文件并读取
        val bf = BufferedReader(
            InputStreamReader(
                assetManager.open(fileName)
            )
        )
        var line: String?
        while (bf.readLine().also { line = it } != null) {
            stringBuilder.append(line)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return stringBuilder.toString()
}


//Kotlin通用扩展

fun isHasConfig(): Boolean {
    var configInfo = AppPrefsUtils.getConfigInfo()
    return configInfo != null
}

fun afterNoSetConfig(method: () -> Unit) {
    if (!isHasConfig()) {
        method()
    }
}

fun afterSetConfigInfo(code: String, onSuccess: () -> Unit, onFail: (msg:String) -> Unit) {
    if (TextUtils.isEmpty(code)){
        onFail("公司编码不能为空！")
    }else{
        var configInfo = AppPrefsUtils.setConfigInfo(code)
        if (configInfo != null) {
            onSuccess()
        }else{
            onFail("请输入正确的公司编码!")
        }
    }
}

fun getMessageAppID():Int{
    var configInfo = AppPrefsUtils.getConfigInfo()
    if (configInfo==null){
        LogUtils.e("configInfo","getMessageAppID,configInfo is null")
        return -1
    }
    return configInfo.MessageAppID
}

fun getCompanyCode():String{
    var configInfo = AppPrefsUtils.getConfigInfo()
    if (configInfo==null){
        LogUtils.e("configInfo","getCompanyCode,configInfo is null")
        return ""
    }
    return configInfo.CompanyCode
}

fun getServerAddress():String{
    var configInfo = AppPrefsUtils.getConfigInfo()
    if (configInfo==null){
        LogUtils.e("configInfo","getServerAddress,configInfo is null")
        return ""
    }
    return configInfo.ServerAddress
}

fun getIp():String{
    var configInfo = AppPrefsUtils.getConfigInfo()
    if (configInfo==null){
        LogUtils.e("configInfo","getIp,configInfo is null")
        return ""
    }
    return configInfo.ip
}
