package com.linwei.cams.service.base.model

import android.os.Parcel
import android.os.Parcelable

class UserInfoBean : Parcelable {
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

    override fun toString(): String {
        return "UserInfoBean(email=$email, icon=$icon, id=$id, password=$password, token=$token, type=$type, username=$username, chapterTops=$chapterTops, collectIds=$collectIds, coinCount=$coinCount, level=$level, rank=$rank, userId=$userId, reason=$reason, desc=$desc, date=$date)"
    }


    companion object CREATOR : Parcelable.Creator<UserInfoBean?> {
        override fun createFromParcel(parcel: Parcel): UserInfoBean {
            return UserInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}