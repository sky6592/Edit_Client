package com.doublejj.edit.ui.modules.main.myedit.my_comment.open_comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.doublejj.edit.ApplicationClass
import com.doublejj.edit.R
import com.doublejj.edit.data.models.comment.CommentData
import com.doublejj.edit.databinding.ActivityLookupMyCommentBinding
import com.doublejj.edit.ui.modules.main.home.open_comment.OpenCommentAdapter

class LookupMyCommentActivity : AppCompatActivity() {
    private val TAG: String = javaClass.simpleName.toString()
    private lateinit var binding: ActivityLookupMyCommentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lookup_my_comment)
        binding.lifecycleOwner = this

        /** get comments from server **/
        getComments()

        /** set adapter **/
        setAdapter()

        /** toolbar buttons **/
        binding.ibBack.setOnClickListener {
            finish()
        }
        binding.ibRefresh.setOnClickListener {
            // refresh data
            onResume()
        }

        val coverLetterId = intent.getLongExtra("coverLetterId", 0L)
        val userProfile = intent.getStringExtra("userProfile")?:"purple/relief"
        val nickName = intent.getStringExtra("nickName")?:""
        val jobName = intent.getStringExtra("jobName")?:""
        val coverLetterCategoryName = intent.getStringExtra("coverLetterCategoryName")?:""
        val coverLetterContent = intent.getStringExtra("coverLetterContent")?:""

        val characterResId = (applicationContext as ApplicationClass).getCharacterResId(userProfile)
        binding.ivCharacter.setImageResource(characterResId)
        binding.tvSentenceWriter.text = nickName
        binding.tvOccupationType.text = jobName
        binding.tvSelfWritingType.text = coverLetterCategoryName
        binding.tvSentenceContent.text = coverLetterContent

        binding.llZeroComment.visibility = View.GONE
    }

    fun getComments() {
        // TODO : 리스트 형태로 response 바뀌면 주석 해제 후 페이지 넣기
        // TODO : 무한스크롤 처리
//        SentencesOfCommentService(this).tryGetSentencesOfComment(page = 1)
        
        // TODO : 리스트 형태로 response 바뀌면 아래 코드 수정
        val commentDataList = mutableListOf<CommentData>()
        commentDataList.add(
            CommentData(
                commentId = intent.getLongExtra("commentId", 0L),
                userProfile = intent.getStringExtra("commentUserProfile")?:"purple/relief",
                nickName = intent.getStringExtra("commentNickName")?:"",
                jobName = intent.getStringExtra("commentJobName")?:"",
                sentenceEvaluation = intent.getStringExtra("sentenceEvaluation")?:"",
                concretenessLogic = intent.getStringExtra("concretenessLogic")?:"",
                sincerity = intent.getStringExtra("sincerity")?:"",
                activity = intent.getStringExtra("activity")?:"",
                commentContent = intent.getStringExtra("commentContent")?:"",
                isAdopted = intent.getStringExtra("isAdopted")?:"",
                isAppreciated = intent.getBooleanExtra("isAppreciated", false),
                isMine = intent.getBooleanExtra("isMine", true)
            )
        )
        binding.rvComment.adapter = OpenCommentAdapter(this, commentDataList, isMySentence = true, supportFragmentManager)
    }

    fun setAdapter() {
        binding.rvComment.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        getComments()
    }
}