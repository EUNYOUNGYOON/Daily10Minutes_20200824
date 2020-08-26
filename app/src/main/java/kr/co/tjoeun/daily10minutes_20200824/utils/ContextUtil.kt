package kr.co.tjoeun.daily10minutes_20200824.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "Daily10MinutesPref"
        private val AUTO_LOGIN_CHECK = "AUTO_LOGIN_CHECK"

        fun setAutoLoginCheck (context: Context, isAuto: Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN_CHECK, isAuto).apply() //옛날에는 commit()인데 요즘은  apply()

        }

        fun getAutoLoginCheck (context: Context) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN_CHECK, false) // 저장된게 하나도 없을 때 기본값을 어떤걸로 주는지를 말하는것 디폴트 false
        }

    }
}