package kr.co.tjoeun.daily10minutes_20200824

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            // 이 받아낸 id/pw를 어떻게 서버에 로그인 확인으로 요청하는가?
            // new 인터페이스()
            ServerUtil.postRequestLogin(inputId,inputPw, object :ServerUtil.JsonResponseHandler {
                override fun onRespnse(json: JSONObject) {

                    //실제로 응답이 돌아왔을 때 실행시켜줄 내용들
                    // 아이디 비번 : kj_cho@nepp.kr / Test!1234
                    Log.d("메인화면에서 응답확인", json.toString())

                    val codeNum = json.getInt("code")
                    if(codeNum == 200){
                        // 서버 개발자가 로그인 성공일 때는 code를 200으로 준다.
                        // 로그인 성공시에 대한 코드 정의
                        Log.d("로그인시도", "성공상황")

                        // 그냥 쓰면 앱 죽고 runOnUiThread 안에서 실행시켜야한다.
                        runOnUiThread {
                            Toast.makeText(mContext,"로그인 성공", Toast.LENGTH_SHORT).show()
                        }

                    }
                    else {
                        // 그 외의 숫자는 실패
                        Log.e("로그인시도", "실패상황")
                        runOnUiThread {
                            Toast.makeText(mContext,"로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

    }

    override fun setValues() {

    }

}