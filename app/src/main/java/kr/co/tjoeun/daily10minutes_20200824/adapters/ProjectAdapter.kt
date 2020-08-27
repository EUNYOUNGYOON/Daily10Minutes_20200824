package kr.co.tjoeun.daily10minutes_20200824.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kr.co.tjoeun.daily10minutes_20200824.R
import kr.co.tjoeun.daily10minutes_20200824.datas.Project

// val을 붙이고 안붙이고의 차이는 val이 들어간 변수는 class 안 코드에서 사용할 변수
class ProjectAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<Project>) : ArrayAdapter<Project>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if(tempRow == null)
        {
            tempRow = inf.inflate(R.layout.project_list_item, null)
        }

        val row = tempRow!!
        return row



    }

}