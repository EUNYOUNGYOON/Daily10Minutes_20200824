package kr.co.tjoeun.daily10minutes_20200824

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import kr.co.tjoeun.daily10minutes_20200824.utils.ContextUtil
import kr.co.tjoeun.daily10minutes_20200824.utils.ServerUtil

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        //Handler : 안드로이드에서 쓰레드처럼 동작하도록 도와주는 클래스
        //시간이 오래 걸리는 작업들은 따로 백그라운드 처리. ex. 네트워크 통신
        //UI는 계속 사용자의 동작을 받을 수 있도록 유지 + 다른 작업을 별개의 핸들러를 통해서 실행.
        val myHandler  = Handler(Looper.getMainLooper())
        myHandler.postDelayed({
            // 실제로 시간이 지나고 나서 실행할 내용
            // 자동로그인이 된 상황이면 메인화면으로 : 자동로그인 체크박스 true? 로그인도 되었어야 한다
            // 자동로그인이 안된 상황이면 로그인 화면으로 이동
            val myIntent : Intent
            if(ContextUtil.getAutoLoginCheck(mContext) && ContextUtil.getLoginUserToken(mContext) != "") {
                //자동로그인이 OK
                myIntent = Intent(mContext, MainActivity::class.java)
            }
            else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }
            startActivity(myIntent)
            finish()

        }, 2500) // 지연시킬시간-ms단위로

    }


}