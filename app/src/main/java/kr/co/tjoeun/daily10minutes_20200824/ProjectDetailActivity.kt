package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_project_detail.*
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class ProjectDetailActivity : BaseActivity() {

    lateinit var mProject : Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("projectInfo") as Project

        //이미지뷰 같은경우는 Glide로 사용하는거
        Glide.with(mContext).load(mProject.imgUrl).into(projectImg)
        titleTxt.text = mProject.title
        descTxt.text = mProject.desc

    }

    override fun onResume() {
        super.onResume()
        getProjectDetailFromServer()
    }

    fun getProjectDetailFromServer() {

        ServerUtil.getRequestProjectDetailById(mContext, mProject.id, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val projectObj = data.getJSONObject("project")

                mProject = Project.getProjectFromJson(projectObj)

                runOnUiThread {
                    proofTxt.text = mProject.proof_method
                    memsTxt.text = "(현재 참여 인원 : ${mProject.ongoing_users_count.toString()} 명)"
                }

            }

        })
    }


}