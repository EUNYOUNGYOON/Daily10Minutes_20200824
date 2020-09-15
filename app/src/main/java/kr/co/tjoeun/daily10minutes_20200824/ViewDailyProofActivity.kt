package kr.co.tjoeun.daily10minutes_20200824

import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_view_daily_proof.*
import java.util.*

class ViewDailyProofActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_daily_proof)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        // 날짜선택 버튼을 클릭했을 때
        selectDateBtn.setOnClickListener {

            val dataPickerDialog = DatePickerDialog(mContext, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

                // 날짜가 선택되면 실행할 코드
                Log.d("선택된 년", year.toString())
                Log.d("선택된 월", month.toString())
                Log.d("선택된 일", dayOfMonth.toString())

                // 실생활 : 1~12월
                // JAVA / Kotlin : 0~11월


                //2020, 9, 15)
            }, 2020, Calendar.JUNE, 9)
            dataPickerDialog.show()


        }

    }

    override fun setValues() {

    }
}