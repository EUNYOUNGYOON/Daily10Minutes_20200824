package kr.co.tjoeun.daily10minutes_20200824.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kr.co.tjoeun.daily10minutes_20200824.R
import kr.co.tjoeun.daily10minutes_20200824.datas.Project
import kr.co.tjoeun.daily10minutes_20200824.datas.User

class UserAdapter (
    val mContext: Context,
    resId: Int,
    val mList: List<User>) : ArrayAdapter<User>(mContext, resId, mList) {
    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if(tempRow == null)
        {
            tempRow = inf.inflate(R.layout.user_list_item, null)
        }

        val row = tempRow!!

        // 실제데이터 넣어주기
        val data = mList[position]
        val imgTxt = row.findViewById<ImageView>(R.id.userImg)
        val nickNameTxt = row.findViewById<TextView>(R.id.userNickNameTxt)
        val emailTxt = row.findViewById<TextView>(R.id.userEmailTxt)

        nickNameTxt.text = data.nickName
        emailTxt.text = data.email
        //이미지뷰 같은경우는 Glide로 사용하는거
        Glide.with(mContext).load(data.profileImageArrayList[0]).into(imgTxt)
        return row

    }

}