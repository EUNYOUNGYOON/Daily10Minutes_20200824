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
            // json 변수 내의 profile_images JSONArray를 추가로 파싱하자

            val pfImgJsonArr = json.getJSONArray("profile_images")
            for (i in 0 until pfImgJsonArr.length()) {

                // 프사 정보를 담고 있는 JSONObject 하나하나 추출
                val pfImgObj = pfImgJsonArr.getJSONObject(i)

                // 따낸 JSONObject 안에서 이미지 주소 String 만 추출 -> user의 프사 목록에 추가
                val imageUrl = pfImgObj.getString("img_url")
                user.profileImageArrayList.add(imageUrl)

            }


            return user


        }

    }

}