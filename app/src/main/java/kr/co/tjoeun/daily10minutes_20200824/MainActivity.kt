package kr.co.tjoeun.daily10minutes_20200824

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun setupEvents() {

        loginBtn.setOnClickListener {
            val inputId = idEdt.text.toString()
            val inputPw = pwEdt.text.toString()

            // 이 받아낸 id/pw를 어떻게 서버에 로그인 확인으로 요청하는가?



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