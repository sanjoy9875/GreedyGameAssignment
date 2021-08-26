package com.example.myapplication.adapter

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


class SavedItemsAdapter(private var entity: List<ResponseEntity>, private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<SavedItemsAdapter.EntityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.save_item_layout, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {

        Glide.with(holder.view)
            .load(entity[position].urlToImage)
            .into(holder.mIvItemImage)

        holder.mTvItemTitle.text = entity[position].title
        holder.mTvAuthor.text = entity[position].author
        holder.mTvItemSource.text = entity[position].source

        val str = entity[position].publishedAt
        var date = ""

        for(i in 0..(str?.length?.minus(1)!!)){

            if (str[i] == 'T'){
                break
            }
            date += str[i]
        }
        holder.mTvItemDate.text = date

        holder.itemView.setOnClickListener {
            onItemClick.onEntityItemClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return entity.size
    }

    fun filteredList(tempList: MutableList<ResponseEntity>) {
        entity = tempList
        notifyDataSetChanged()
    }

    class EntityViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val mIvItemImage: ImageView = view.findViewById(R.id.ivArticleImage)
        val mTvItemSource: TextView = view.findViewById(R.id.tvSourceName)
        val mTvItemTitle: TextView = view.findViewById(R.id.tvTitle)
        val mTvItemDate: TextView = view.findViewById(R.id.tvDate)
        val mTvAuthor: TextView = view.findViewById(R.id.tvAuthorName)


    }
}