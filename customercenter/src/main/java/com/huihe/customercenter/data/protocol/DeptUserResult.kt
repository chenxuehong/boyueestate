package com.huihe.customercenter.data.protocol

import android.os.Parcelable
import com.huihe.customercenter.ui.widget.ISearchResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class DeptUserResult(var createUserList: String? = null) : ISearchResult,Parcelable