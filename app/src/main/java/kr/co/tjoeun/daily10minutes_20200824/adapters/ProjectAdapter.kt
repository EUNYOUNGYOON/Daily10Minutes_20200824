package kr.co.tjoeun.daily10minutes_20200824.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20200824.R
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import org.w3c.dom.Text

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

        // 실제데이터 넣어주기
        val data = mList[position]
        val imgTxt = row.findViewById<ImageView>(R.id.projectImg)
        val titleTxt = row.findViewById<TextView>(R.id.projectTitle)
        val descTxt = row.findViewById<TextView>(R.id.projectDesc)

        titleTxt.text = data.title
        descTxt.text = data.desc
        //이미지뷰 같은경우는 Glide로 사용하는거
        Glide.with(mContext).load(data.imgUrl).into(imgTxt)

        return row

    }

}