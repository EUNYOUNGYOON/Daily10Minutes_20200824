package kr.co.tjoeun.daily10minutes_20200824

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ContextUtil
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject
import kotlin.math.sign

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            Log.d("자동로그인체크여부", isChecked.toString())
            ContextUtil.setAutoLoginCheck(mContext,isChecked)
        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            // 이 받아낸 id/pw를 어떻게 서버에 로그인 확인으로 요청하는가?
            // new 인터페이스()
            ServerUtil.postRequestLogin(inputId,inputPw, object :ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    //실제로 응답이 돌아왔을 때 실행시켜줄 내용들
                    // 아이디 비번 : kj_cho@nepp.kr / Test!1234
                    Log.d("메인화면에서 응답확인", json.toString())

                    val codeNum = json.getInt("code")
                    // 사유
                    val message = json.getString("message")
                    
                    if(codeNum == 200){
                        // 서버 개발자가 로그인 성공일 때는 code를 200으로 준다.
                        // 로그인 성공시에 대한 코드 정의
                        Log.d("로그인시도", "성공상황")

                        // 그냥 쓰면 앱 죽고 runOnUiThread 안에서 실행시켜야한다.
//                        runOnUiThread {
//                            Toast.makeText(mContext, "로그인성공", Toast.LENGTH_SHORT).show()
//                        }

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        // 메인으로 이동 후에는 로그인화면은 종료하자
                        // 뒤로가기 버튼을 눌러도 다시 로그인화면으로 오는것이 아니라 메인화면으로 그대로 유지하기 때문에 finish로 끝내자
                        finish()

                    }
                    else {
                        // 그 외의 숫자는 실패
                        // 서버가 알려주는 사유 토스트로 출력
                        Log.e("로그인시도", "실패상황")
                        runOnUiThread {
                            Toast.makeText(mContext, message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }

    }

    override fun setValues() {

        // 자동로그인 체크박스 적용한 데이터를 앱 다시 들어올 때 getter로 가져와서 화면 세팅해주기
        autoLoginCheckBox.isChecked = ContextUtil.getAutoLoginCheck(mContext)

    }

}