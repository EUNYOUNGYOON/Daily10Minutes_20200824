package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjectList = ArrayList<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        getProjectListFromServer()
    }

    fun getProjectListFromServer(){
        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val dataObj = json.getJSONObject("data")
                val projectArr = dataObj.getJSONArray("projects")

                // 프로젝트가 10개 반복
                for (i in 0 until projectArr.length())
                {
                    val projectObj = projectArr.getJSONObject(i)

                    val project = Project()
                    project.id = projectObj.getInt("id")
                    project.title = projectObj.getString("title")
                    project.imgUrl = projectObj.getString("img_url")
                    project.desc = projectObj.getString("description")

                    mProjectList.add(project)



                }


            }

        })
    }


}