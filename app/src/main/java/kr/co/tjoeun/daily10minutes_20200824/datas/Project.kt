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

            return proj
        }
    }
}