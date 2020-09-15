package kr.co.tjoeun.daily10minutes_20200824.datas

import org.json.JSONObject
import java.io.Serializable

class Project : Serializable {

    var id = 0
    var title = ""
    var imgUrl = ""
    var desc = ""
    var ongoing_users_count = 0
    var proof_method = ""

    // null이 들어갈 수도 있다. 디폴트가 널이다.
    var myLastStatus : String? = null

    companion object {

        fun getProjectFromJson(json: JSONObject) : Project {

            val proj = Project()
            // 위에서 만든 빈 프로젝트를 json의 데이터를 이용해서 proj 변수의 값을 채워주자

            proj.id = json.getInt("id")
            proj.title = json.getString("title")
            proj.imgUrl = json.getString("img_url")
            proj.desc = json.getString("description")
            proj.ongoing_users_count = json.getInt("ongoing_users_count")
            proj.proof_method = json.getString("proof_method")

            // 서버에서 주는 데이터가 null 일 수도 있거나
            // 아예 상황에 따라 첨부되지 않을 수도 있는 항목은
            // isNull로 체크해서 null 아닐 때만 파싱하는것이 앱의 안정성을 높인다

            if(!json.isNull("my_last_status")) {
                proj.myLastStatus = json.getString("my_last_status")
            }

            return proj
        }
    }
}