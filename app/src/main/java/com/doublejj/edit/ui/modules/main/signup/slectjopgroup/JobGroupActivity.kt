package com.doublejj.edit.ui.modules.main.signup.slectjopgroup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.doublejj.edit.R
import com.doublejj.edit.databinding.ActivityJobGroupBinding
import com.doublejj.edit.ui.modules.main.signup.read.ReadActivity

class JobGroupActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityJobGroupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_job_group)

        //다음으로 버튼
        mBinding.btnJobGroup.setOnClickListener {
            startActivity(Intent(this, ReadActivity::class.java))
        }

        /** set adapter for spinner **/
        val typeAdapter = object : ArrayAdapter<String>(this, R.layout.item_spinner) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                if (position == count) {
                    val tvItemSpinner = view.findViewById<TextView>(R.id.tv_item_spinner)
                    // 마지막 position의 textView를 hint로 사용
                    tvItemSpinner.text = ""
                    // item의 마지막 값을 불러와 hint로 추가
                    tvItemSpinner.hint = getItem(count)
                }
                return view
            }

            override fun getCount(): Int {
                // 마지막 item은 hint로만 사용하기에 1 빼기
                return super.getCount() - 1
            }
        }
        // item과 hint 추가, adapter에 연결
        val typeStringArray = resources.getStringArray(R.array.array_sentence_type)
        typeAdapter.addAll(typeStringArray.toMutableList())
        typeAdapter.add(getString(R.string.spinner_hint))
        mBinding.spinnerJobGroup.adapter = typeAdapter

        // settings for adapter
        mBinding.spinnerJobGroup.setSelection(typeAdapter.count)
        mBinding.spinnerJobGroup.setPopupBackgroundResource(R.drawable.shape_white_bg_round_4dp)

        // convert dp to px
//        mBinding.spinnerJobGroup.dropDownVerticalOffset = dipToPixels(40f)

        mBinding.spinnerJobGroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // 서버에 넣을 카테고리 값 1부터 시작
//                writingRequest.coverLetterCategoryId = (position+1).toLong()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
//                writingRequest.coverLetterCategoryId = null
                }
            }

    }

    fun dipToPixels(dipValue: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dipValue,
            resources.displayMetrics
        ).toInt()
    }
}