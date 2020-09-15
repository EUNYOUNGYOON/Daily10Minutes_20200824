package kr.co.tjoeun.daily10minutes_20200824

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_project_detail.*
import kotlinx.android.synthetic.main.activity_view_project_members.*
import kr.co.tjoeun.daily10minutes_20200824.adapters.UserAdapter
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import kr.co.tjoeun.daily10minutes_20200824.datas.User
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class ViewProjectMembersActivity : BaseActivity() {

    //프로젝트 상세화면에서 넘겨준, 프로젝트 데이터를 담기 위한 멤버변수
    lateinit var mProject: Project

    // user List 추가
    lateinit var mUserAdapter: UserAdapter
    val mUserList = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project_members)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mProject = intent.getSerializableExtra("project") as Project

        getUserListFromServer()
        mUserAdapter = UserAdapter(mContext, R.layout.user_list_item, mUserList)
        membersListView.adapter = mUserAdapter

    }

    fun getUserListFromServer() {

        ServerUtil.getRequestUserById(mContext, mProject.id, object: ServerUtil.JsonResponseHandler{
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val projectObj = data.getJSONObject("project")
                val ongoingUserArr = projectObj.getJSONArray("ongoing_users")

                for( i in 0 until ongoingUserArr.length()){

                    val memberObj = ongoingUserArr.getJSONObject(i)

                    // memberObj -> List형태로 변환 해줘야지 ArrayList에 추가
                    val user = User.getUserFromJson(memberObj)
                    mUserList.add(user)

                }

            }


        })
    }


}