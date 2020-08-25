package kr.co.tjoeun.daily10minutes_20200824.utils

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    // 화면(액티비티)의 입장에서 서버 응답이 돌아왔을 때 실행해줄 내용을 담기위한 인터페이스
    // 로그인을 갔다가 돌아오면 어떻게 할건지
    interface JsonResponseHandler {
        fun onRespnse(json : JSONObject)
    }

    companion object {

        // 이 영역 안에 만드는 변수 or 함수는 객체를 이용하지 않고,
        // 클래스 자체의 기능으로 활용 ( JAVA의 static처럼 활용 가능)

        val BASE_URL = "http://15.164.153.174"

        // 로그인 기능 -> 로그인을 수행하는 함수 작성
        fun postRequestLogin(id: String, pw: String, handler: JsonResponseHandler?) {

            // 안드로이드 앱이 클라이언트로 동작하도록 도와주자
            val client = OkHttpClient()

            // 어느 기능으로 갈건지 주소 완성 -> http://호스트주소/user
            val urlStr = "${BASE_URL}/user"

            // 파라미터들을 미리 담아두자 -> POST(/PUT/PATCH) - formData 활용
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            // 목적지의 정보를 Request 형태로 완성하자 (티켓 최종 발권)
            val request = Request.Builder()
                .url(urlStr)
                .post(formData)
                .build()

            // 미리 만들어둔 클라이언트 변수를 활용해서 request변수에 적힌 정보로 서비에 요청 날리기(호출 - call)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 서버 연결 자체에 실패
                }

                override fun onResponse(call: Call, response: Response) {
                    // 결과가 성공이든 실패든 상관없이 서버가 뭔가 답변을 해준 경우
                    // 응답이 돌아온 경우

                    // 서버가 내려준 응답의 본문을 string 형ㅇ태로 저장
                    val bodyString = response.body!!.string()

                    // 받아낸 string을 -> 분석하기 용이한 JSONobject 형태로 변환
                    val json = JSONObject(bodyString)
                    Log.d("서버응답본문" , json.toString())

                    //어떤 처리를 해줄지 가이드북(인터페이스)이 존재한다면,
                    // 그 가이드북에 적힌 내용을 실제로 실행
                    handler?.onRespnse(json)

                }
            })
        }

        // 중복확인
        fun getRequestEmailCheck(email: String, handler: JsonResponseHandler?) {

            //서버에 Request를 날려주는 클라이언트 역할을 돕는 변수 미리 만들기
            val client = OkHttpClient()

            // 어느 기능으로 갈건지 주소 완성
            // url(호스트주소 + 기능주소)을 만드는 과정에서 필요 파라미터도 가공 첨부
            val urlBuilder = "${BASE_URL}/email_check".toHttpUrlOrNull()!!.newBuilder()
            // url 가공기를 이용해서 필요한 데이터를 첨부
            urlBuilder.addEncodedQueryParameter("email", email)

            // 가공이 끝난 url을 가지고 urlStr으로 완성
            val urlStr = urlBuilder.build().toString()

            // 임시 : 어떻게 url이 가공되었는지 로그로 확인하기 위해
            Log.d("완성된 url", urlStr)

            // 요청정보를 담는 request
            val request = Request.Builder()
                .url(urlStr)
                .get()
                .build()

            // 미리 만들어둔 클라이언트 변수를 활용해서 request변수에 적힌 정보로 서비에 요청 날리기(호출 - call)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("서버응답본문", json.toString())

                    handler?.onRespnse(json)
                }

            })

        }
    }


}