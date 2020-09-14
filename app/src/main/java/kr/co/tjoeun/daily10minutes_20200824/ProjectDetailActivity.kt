package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_project_detail.*
import kr.co.tjoeun.daily10minutes_20200824.datas.Project

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
        memsTxt.text = ""
        authTxt.text = ""




    }


}