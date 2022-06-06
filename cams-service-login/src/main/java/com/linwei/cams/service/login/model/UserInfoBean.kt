package com.linwei.cams.service.login.model

import android.os.Parcel
import android.os.Parcelable

class UserInfo : Parcelable {
    var email: String? = null
    var icon: String? = null
    var id: String? = null
    var password: String? = null
    var token: String? = null
    var type: String? = null
    var username: String? = null
    var chapterTops: List<*>? = null
    var collectIds //收藏的文章id
            : List<Int>? = null
    var coinCount: String? = null
    var level = 0
    var rank: String? = null
    var userId: String? = null
    var reason: String? = null
    var desc: String? = null
    var date: String? = null

    constructor()
    protected constructor(parcel: Parcel) {
        email = parcel.readString()
        icon = parcel.readString()
        id = parcel.readString()
        password = parcel.readString()
        token = parcel.readString()
        type = parcel.readString()
        username = parcel.readString()
        coinCount = parcel.readString()
        level = parcel.readInt()
        rank = parcel.readString()
        userId = parcel.readString()
        reason = parcel.readString()
        desc = parcel.readString()
        date = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(email)
        dest.writeString(icon)
        dest.writeString(id)
        dest.writeString(password)
        dest.writeString(token)
        dest.writeString(type)
        dest.writeString(username)
        dest.writeString(coinCount)
        dest.writeInt(level)
        dest.writeString(rank)
        dest.writeString(userId)
        dest.writeString(reason)
        dest.writeString(desc)
        dest.writeString(date)
    }

    companion object CREATOR : Parcelable.Creator<UserInfo?> {
        override fun createFromParcel(parcel: Parcel): UserInfo {
            return UserInfo(parcel)
        }

        override fun newArray(size: Int): Array<UserInfo?> {
            return arrayOfNulls(size)
        }
    }
}