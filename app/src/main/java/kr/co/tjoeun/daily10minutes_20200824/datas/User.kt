package kr.co.tjoeun.daily10minutes_20200824.datas

import org.json.JSONObject

class User {

    var id = 0
    var email = ""
    var nickName = ""

    val profileImageArrayList = ArrayList<String>()

    companion object {

        fun getUserFromJson(json: JSONObject) : User {

            //기본값으로 세팅된 User를 만들고 -> Json 변수를 이용해서 내용물을 채우고 결과로 return
            val user = User()

            // json으로 user변수의 항목들을 채우자
            user.id = json.getInt("id")
            user.nickName = json.getString("nick_name")
            user.email = json.getString("email")
            
            // 사용자 목록에도 파싱을 해줘야함


            return user


        }

    }

}