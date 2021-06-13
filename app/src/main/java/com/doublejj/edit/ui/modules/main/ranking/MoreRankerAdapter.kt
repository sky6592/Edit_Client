package com.doublejj.edit.ui.modules.main.ranking

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.doublejj.edit.R
import com.doublejj.edit.data.models.ranking.UserRankingResult

class MoreRankerAdapter(
    private val context: Context,
    private val rankerList: MutableList<UserRankingResult>,
    private val role: String
) : RecyclerView.Adapter<MoreRankerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_rank_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rankData: UserRankingResult = rankerList.get(position)

        holder.tvRankNumber.text = rankData.rankId.toString()
        holder.tvRankNickname.text = rankData.nickName
        holder.tvRankPosition.text = rankData.jobName

        holder.llRankItem.setOnClickListener {
            val sendIntent = Intent(context, DetailedRankerActivity::class.java)
            sendIntent.putExtra("role", role)
            sendIntent.putExtra("userId", rankData.userId)
            sendIntent.putExtra("rankId", rankData.rankId)
            sendIntent.putExtra("nickName", rankData.nickName)
            context.startActivity(sendIntent)
        }
    }

    override fun getItemCount(): Int {
        return rankerList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRankNumber: TextView = itemView.findViewById(R.id.tv_rank_number)
        val tvRankNickname: TextView = itemView.findViewById(R.id.tv_rank_nickname)
        val tvRankPosition: TextView = itemView.findViewById(R.id.tv_rank_position)
        val llRankItem: LinearLayout = itemView.findViewById(R.id.ll_rank_item)
    }
}