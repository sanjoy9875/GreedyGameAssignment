package com.example.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.model.ArticlesModel
import com.example.myapplication.data.model.ResponseEntity


class ItemsAdapter(private var entity: List<ArticlesModel>, private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ItemsAdapter.EntityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_layout, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {

        Glide.with(holder.view)
            .load(entity[position].urlToImage)
            .into(holder.mIvItemImage)

        holder.mTvItemTitle.text = entity[position].title
        holder.mTvItemContent.text = entity[position].content

        val str = entity[position].publishedAt
        var date = ""

        for(i in 0..(str?.length?.minus(1)!!)){

            if (str[i] == 'T'){
                break
            }
            date += str[i]
        }
        holder.mTvItemDate.text = date

        holder.mBtnRead.setOnClickListener {
            onItemClick.onEntityItemClicked(position)
        }

        holder.mBtnSave.setOnClickListener {
            onItemClick.onSaveClicked(position)
        }

//        holder.itemView.setOnClickListener {
//            onItemClick.onEntityItemClicked(position)
//        }
    }

    override fun getItemCount(): Int {
        return entity.size
    }

    fun filteredList(tempList: MutableList<ArticlesModel>) {
        entity = tempList
        notifyDataSetChanged()
    }

    class EntityViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val mIvItemImage: ImageView = view.findViewById(R.id.ivArticleImage)
        val mTvItemTitle: TextView = view.findViewById(R.id.tvArticleTitle)
        val mTvItemContent: TextView = view.findViewById(R.id.tvArticleContent)
        val mTvItemDate: TextView = view.findViewById(R.id.tvArticleDate)
        val mBtnRead: Button = view.findViewById(R.id.btnRead)
        val mBtnSave: Button = view.findViewById(R.id.btnSave)

    }
}