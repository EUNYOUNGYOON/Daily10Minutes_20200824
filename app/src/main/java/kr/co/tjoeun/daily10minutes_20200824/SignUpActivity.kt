package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        // 이메일 입력칸의 내용이 변경된 경우 -> 검사 다시하도록 유도
        signUpEmailEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("입력문구", p0.toString())

                emailCheckResultTxt.text = "중복 확인을 해주세요"
            }

        })

        // 중복확인버튼이 눌렸을 때 하는 행동
        emailCheckBtn.setOnClickListener {
            //입력된 이메일 확인 > 해당 이메일 서버에서 중복검사 기능 요청 -> 결과에 따른 문구 반영
            // 1. 화면구현 이벤트처리
            val inputEmail = signUpEmailEdt.text.toString()
            // 2. API 호출 - get방식 /email_check /query
            ServerUtil.getRequestEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler{
                override fun onRespnse(json: JSONObject) {

                    // 중복 가능
                    val codeNum = json.getInt("code")
                    val message = json.getString("message")

                    runOnUiThread {
                        if(codeNum == 200) {
                            emailCheckResultTxt.text = message
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        } else {
                            emailCheckResultTxt.text = message
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            })
        }

    }

    override fun setValues() {

    }
}