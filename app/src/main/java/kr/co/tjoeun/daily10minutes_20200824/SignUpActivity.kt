package kr.co.tjoeun.daily10minutes_20200824

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    // 아이디 중복검사 통과여부
    var isIdOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

            // 아이디를 사용해도 되는지? (중복검사를 통과했는지?)
            if (!isIdOk) {
                
                // 사용하면 안된느 경우 -> 회원가입 이벤트를 강제 종료
                Toast.makeText(mContext, "아이디 중복검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 아이디를 사용해도 되는지? (중복검사를 통과했는지?)
            if (signUpPwEdt.text.length <8) {

                // 사용하면 안된느 경우 -> 회원가입 이벤트를 강제 종료
                Toast.makeText(mContext, "비밀번호 8글자 이상 작성해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //사용해도 된다면 다음 로직 진행
            // 닉네임은 한번정하면 변경이 불가능합니다. 정말 회원가입 하시겠습니까?
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("회원가입 안내")
            alert.setMessage("닉네임은 한번정하면 변경이 불가능합니다. 정말 회원가입 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

                val inputId = signUpEmailEdt.text.toString()
                val inputPw = signUpPwEdt.text.toString()
                val inputNick = signUpNickNameEdt.text.toString()

                //실제 회원가입기능 (API) 호출
                ServerUtil.putRequestSignUp(inputId, inputPw, inputNick, object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(json: JSONObject) {

                        //연습문제
                        // 1. 회원가입에 성공한 경우, "회원이 되신것을 환영합니다." 토스트 + 로그인화면 복귀
                        // 2. 회원가입에 실패한 경우, 왜 실패했는지 서버가 알려주는 메시지 토스트출력

                        val code = json.getInt("code")
                        val msg = json.getString("message")

                        runOnUiThread {
                            if (code == 200) {
                                Toast.makeText(mContext, "회원이 되신 것을 환영합니다.", Toast.LENGTH_SHORT)
                                    .show()
                                // 로그인화면으로 복귀
                                //val myIntent = Intent(mContext, LoginActivity::class.java)
                                //startActivity(myIntent)
                                finish()
                            } else {
                                // 실패한 경우에는 메시지 그대로 출력
                                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                })
            })

            alert.setNegativeButton("취소", null)
            alert.show()


        }

        // 비밀번호 입력칸의 내용이 변경된 경우
        // 입력된 비번 길이에 따른 문구 출력
        // 0 글자 : 비밀번호를 입력해주세요.
        // 8글자 미만 : 비밀번호가 너무 짧습니다.
        // 8글자 이상 : 사용해도 좋은 비밀번호입니다.
        signUpPwEdt.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    when {
                        p0.isEmpty() -> {
                            pwCheckResultTxt.text = "비밀번호를 입력해주세요."
                        }
                        p0.length < 8 -> {
                            pwCheckResultTxt.text = "비밀번호가 너무 짧습니다."
                        }
                        else -> {
                            pwCheckResultTxt.text = "사용해도 좋은 비밀번호입니다."
                        }
                    }
                }
                else
                {
                    pwCheckResultTxt.text = "비밀번호를 입력해주세요."
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        
        // 이메일 입력칸의 내용이 변경된 경우 -> 검사 다시하도록 유도
        signUpEmailEdt.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("입력문구", p0.toString())

                emailCheckResultTxt.text = "중복 확인을 해주세요."
                isIdOk = false
            }

        })

        // 중복확인버튼이 눌렸을 때 하는 행동
        emailCheckBtn.setOnClickListener {
            //입력된 이메일 확인 > 해당 이메일 서버에서 중복검사 기능 요청 -> 결과에 따른 문구 반영
            // 1. 화면구현 이벤트처리
            val inputEmail = signUpEmailEdt.text.toString()
            // 2. API 호출 - get방식 /email_check /query
            ServerUtil.getRequestEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(json: JSONObject) {

                    // 중복 가능
                    val codeNum = json.getInt("code")
                    val message = json.getString("message")

                    runOnUiThread {
                        if(codeNum == 200) {
                            emailCheckResultTxt.text = message
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            isIdOk = true
                        } else {
                            emailCheckResultTxt.text = message
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            isIdOk = false
                        }
                    }
                }
            })
        }

    }

    override fun setValues() {

    }
}