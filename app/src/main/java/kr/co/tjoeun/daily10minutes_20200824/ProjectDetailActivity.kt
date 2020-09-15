package kr.co.tjoeun.daily10minutes_20200824

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        viewAllMemsBtn.setOnClickListener {

            val myIntent = Intent(mContext, ViewProjectMembersActivity::class.java)
            startActivity(myIntent)
        }

        // 신청하기 버튼을 누르면 진짜 신청할건지? 확인(AlertDialog)되면 신청처리
        applyBtn.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("프로젝트 참가 확인")
            alert.setMessage("${mProject.title} 프로젝트에 참가하시겠습니까?") //메시지 : ex) 독서하기 프로젝트에 참가하시겠습니까?
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, which ->
                //실제로 서버 신청처리
                ServerUtil.postRequestApplyProject(mContext, mProject.id, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(json: JSONObject) {

                        // 자동 새로고침이 구현은 되지만 서버를 한번 더 다녀와야 한다
                        // 신청 결과에서 알려주는 데이터를 화면에 반영
                        //getProjectDetailFromServer()
                        val code = json.getInt("code")
                        if(code == 200) {
                            val data = json.getJSONObject("data")
                            val projectObj = data.getJSONObject("project")
                            mProject = Project.getProjectFromJson(projectObj)

                            runOnUiThread {
                                Toast.makeText(mContext,"프로젝트 참가신청 완료", Toast.LENGTH_SHORT).show()

                                //인원수/ 참가 버튼 등 UI변경
                                refreshProjectUI()

                            }
                        }
                    }


                })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

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

                    refreshProjectUI()
//                    proofTxt.text = mProject.proof_method
//                    memsTxt.text = "(현재 참여 인원 : ${mProject.ongoing_users_count.toString()} 명)"
//
//                    // myLastStatus 마지막으로 변경된 프로젝트 신청 상태
//                    // null : 신청한 적이 없다.
//                    // ongoing : 신청해서 진행중
//                    // fail : 중도포기 or 3일 연속 인증글 X 자동포기
//                    // complete :  프로젝트마다 다른 일 짜리 모두 수행 완료
//
//                    // 신청하기 언제? 그 외 모든 상황
//                    // 중도포기 언제? 상태가 ongoing 일 때
//
//
//                    if(mProject.myLastStatus == "ONGOING")
//                    {
//                        giveUpBtn.isEnabled = true
//                        applyBtn.isEnabled = false
//                    } else {
//                        giveUpBtn.isEnabled = false
//                        applyBtn.isEnabled = true
//                    }


                }

            }

        })
    }

    // 서버에서 준 프로젝트 정보를 ui에 새로 반영하는 기능
    fun refreshProjectUI() {

        proofTxt.text = mProject.proof_method
        memsTxt.text = "(현재 참여 인원 : ${mProject.ongoing_users_count.toString()} 명)"

        // myLastStatus 마지막으로 변경된 프로젝트 신청 상태
        // null : 신청한 적이 없다.
        // ongoing : 신청해서 진행중
        // fail : 중도포기 or 3일 연속 인증글 X 자동포기
        // complete :  프로젝트마다 다른 일 짜리 모두 수행 완료

        // 신청하기 언제? 그 외 모든 상황
        // 중도포기 언제? 상태가 ongoing 일 때


        if(mProject.myLastStatus == "ONGOING")
        {
            giveUpBtn.isEnabled = true
            applyBtn.isEnabled = false
        } else {
            giveUpBtn.isEnabled = false
            applyBtn.isEnabled = true
        }

    }


}