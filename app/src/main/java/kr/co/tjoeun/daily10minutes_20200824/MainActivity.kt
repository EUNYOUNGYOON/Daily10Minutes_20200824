package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            // 이 받아낸 id/pw를 어떻게 서버에 로그인 확인으로 요청하는가?
            // new 인터페이스()
            ServerUtil.postRequestLogin(inputId,inputPw, object :ServerUtil.JsonResponseHandler {
                override fun onRespnse(json: JSONObject) {
                    //실제로 응답이 돌아왔을 때 실행시켜줄 내용들
                    Log.d("메인화면에서 응답확인", json.toString())
                }

            })



        }


    }

override fun setValues() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setValues()
    }
}