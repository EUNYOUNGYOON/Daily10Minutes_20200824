package kr.co.tjoeun.daily10minutes_20200824

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200824.adapters.ProjectAdapter
import kr.co.tjoeun.daily10minutes_20200824.adapters.UserAdapter
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import kr.co.tjoeun.daily10minutes_20200824.datas.User
import kr.co.tjoeun.daily10minutes_20200824.utils.ContextUtil
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import okhttp3.internal.notify
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var mProjectAdapter: ProjectAdapter
    val mProjectList = ArrayList<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        logoutBtn.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃을 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                // 실제로그아웃처리
                ContextUtil.setLoginUserToken(mContext, "")
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
                finish()
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

        // 메인화면에서 리스트뷰를 클릭했을 때의 이벤트
        projectListView.setOnItemClickListener { adapterView, view, i, l ->

            val clickedProject = mProjectList[i]
            val myIntent = Intent(mContext, ProjectDetailActivity::class.java)
            //프로젝트 정보를 통째로 넣어주기
            // 빨간줄이 나오는건 Serializable
            // Project.kt에 가서 해당 속성 넣어주기
            myIntent.putExtra("projectInfo", clickedProject)
            startActivity(myIntent)

        }

    }

    override fun setValues() {

        // 등록된 기기 토큰이 어떤건지 확인하는 코드
        //e9iN2unjRMi1ZHApOzwoFA:APA91bH8jTYUy-wNZ69Tl1TyJ9byp3JXMUuLpEXgUAZgEsrpeHKV77b-vh3BFP4VB3K7U6p1uLBYeCHY7tExxra6GL_AmSdZ6hDIMEqHAdAG6H6t-ke2xFPiGmrn0Bwj3z9sVmRaMCMQ
        Log.d("기기토큰", FirebaseInstanceId.getInstance().token!!)
        getProjectListFromServer()
        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjectList)
        projectListView.adapter = mProjectAdapter


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

                    val project = Project.getProjectFromJson(projectObj)
                    // 원래는 이렇게 하나씩 하는것인데 이걸 모듈화로 바꿈
//                    val project = Project()
//                    project.id = projectObj.getInt("id")
//                    project.title = projectObj.getString("title")
//                    project.imgUrl = projectObj.getString("img_url")
//                    project.desc = projectObj.getString("description")
//                    project.ongoing_users_count = projectObj.getInt("ongoing_users_count")
//                    project.proof_method = projectObj.getString("proof_method")

                    mProjectList.add(project)
                }

                // 비동기처리 -> 어댑터 먼저 끝나고, 프로젝트 목록이 나중에 추가될 수 있다.
                // 리스트뷰의 입장에서는 내용물 목록이 변경된 상황 -> notifyDataSetChanged()로 새로고침
                // 그대로 쓰면 앱이 죽어버림
                // 새로고침 : ui에 영향을 주겠다 -> runOnUiThread { } 안에다가 무조건 써야한다.
                runOnUiThread {
                    mProjectAdapter.notifyDataSetChanged()
                }

            }
        })
    }
}