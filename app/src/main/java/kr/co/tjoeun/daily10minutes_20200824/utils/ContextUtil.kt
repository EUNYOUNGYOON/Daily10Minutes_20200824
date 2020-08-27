package kr.co.tjoeun.daily10minutes_20200824.utils

import android.content.Context

class ContextUtil {

    companion object {

        private val prefName = "Daily10MinutesPref"
        private val AUTO_LOGIN_CHECK = "AUTO_LOGIN_CHECK"
        private val LOGIN_USER_TOKEN = "LOGIN_USER_TOKEN"

        fun setAutoLoginCheck (context: Context, isAuto: Boolean) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putBoolean(AUTO_LOGIN_CHECK, isAuto).apply() //옛날에는 commit()인데 요즘은  apply()

        }

        fun getAutoLoginCheck (context: Context) : Boolean {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getBoolean(AUTO_LOGIN_CHECK, false) // 저장된게 하나도 없을 때 기본값을 어떤걸로 주는지를 말하는것 디폴트 false
        }

        // [응용문제] LOGIN_USER_TOKEN 항목에 로그인 성공시 받은 토큰을 저장 / 조회할 기능 추가
        fun setLoginUserToken (context: Context, isToken: String) {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            pref.edit().putString(LOGIN_USER_TOKEN, isToken).apply()
        }

        fun getLoginUserToken (context: Context) : String {
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            return pref.getString(LOGIN_USER_TOKEN, "")!!
        }

    }
}