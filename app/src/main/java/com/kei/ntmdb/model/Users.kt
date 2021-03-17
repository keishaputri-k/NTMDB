package com.kei.ntmdb.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    private var uid : String = "0",
    private var fullName : String = "",
    private var email : String = "",
    private var photo : String = ""
) : Parcelable