package com.doublejj.edit

import android.content.SharedPreferences
import androidx.multidex.MultiDexApplication
import com.doublejj.edit.data.api.interceptors.XAccessTokenInterceptor
import com.doublejj.edit.ui.utils.ActivityList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : MultiDexApplication() {
    companion object {
        // 서버 주소
        const val API_URL = "https://app.team-edit.shop/" // product server
//        const val API_URL = "https://app.team-edit.shop/" // test server


        // 만들어져있는 SharedPreferences를 사용 (재생성하지 않도록 유념)
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token Header 키 값
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        // Retrofit 인스턴스, 앱 실행 시 한번만 생성하여 사용
        lateinit var sRetrofit: Retrofit
        
        // Activity List 관리 클래스
        lateinit var sActivityList: ActivityList

        // User 관련 정보 키 값 (mentor or mentee)
        const val USER_POSITION = "USER-POSITION"
        const val MENTOR_AUTH_CONFIRM = "MENTOR-AUTH-CONFIRM"

        const val USER_NICKNAME = "USER-NICKNAME"
        const val USER_EMOTION = "USER-EMOTION"
        const val USER_COLOR = "USER-COLOR"
    }

    override fun onCreate() {
        super.onCreate()

        // SharedPreferences 인스턴스 최초 생성
        sSharedPreferences = applicationContext.getSharedPreferences("EDIT", MODE_PRIVATE)

        // Retrofit 인스턴스 최초 생성
        initRetrofitInstance()

        // ActivityList 인스턴스 최초 생성
        sActivityList = ActivityList()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    private fun initRetrofitInstance() {
        // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, Interceptor, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun getCharacterResId(code: String) : Int {
        val colorAndEmotion = code.split("/")
        var resPath = "icon_char_"

        when (colorAndEmotion.first()) {
            "purple" -> resPath += "purple_active_"
            "lightPurple" -> resPath += "mid_purple_"
            "blue" -> resPath += "navy_"
            "lightBlue" -> resPath += "sky_"
            "gray" -> resPath += "gray_"
        }
        when (colorAndEmotion.last()) {
            "relief" -> resPath += "0"
            "bigSmile" -> resPath += "1"
            "surprise" -> resPath += "2"
            "happy" -> resPath += "3"
            "smallSmile" -> resPath += "4"
            "wink" -> resPath += "5"
        }
        return applicationContext.resources!!.getIdentifier(resPath, "drawable", packageName)
    }
    fun getCharacterResId(color: String, emotion: String) : Int {
        var resPath = "icon_char_"
        when (color) {
            "purple" -> resPath += "purple_active_"
            "lightPurple" -> resPath += "mid_purple_"
            "blue" -> resPath += "navy_"
            "lightBlue" -> resPath += "sky_"
            "gray" -> resPath += "gray_"
        }
        when (emotion) {
            "relief" -> resPath += "0"
            "bigSmile" -> resPath += "1"
            "surprise" -> resPath += "2"
            "happy" -> resPath += "3"
            "smallSmile" -> resPath += "4"
            "wink" -> resPath += "5"
        }
        return applicationContext.resources!!.getIdentifier(resPath, "drawable", packageName)
    }

    fun getPostionToString(position: String?) : String {
        return when (position) {
            "MENTEE" -> "멘티님"
            else -> "멘토님"
        }
    }

    fun getEvaluationColorId(code: String) : Int {
        return when (code) {
            "부족" -> R.color.red_light
            "보통" -> R.color.yellow_light
            "좋음" -> R.color.green_light
            else -> -1
        }
    }
}