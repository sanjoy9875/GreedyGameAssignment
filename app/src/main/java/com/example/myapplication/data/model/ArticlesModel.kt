package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "entity")
data class ArticlesModel(

	@field:SerializedName("source")
	@ColumnInfo(name = "source")
	val source: SourceModel? = null,

	@field:SerializedName("author")
	@ColumnInfo(name = "author")
	val author: String? = null,

	@field:SerializedName("title")
	@ColumnInfo(name = "title")
	val title: String? = null,

	@field:SerializedName("description")
	@ColumnInfo(name = "description")
	val description: String? = null,

	@field:SerializedName("url")
	@ColumnInfo(name = "url")
	val url: String? = null,

	@field:SerializedName("urlToImage")
	@ColumnInfo(name = "urlToImage")
	val urlToImage: String? = null,

	@field:SerializedName("publishedAt")
	@ColumnInfo(name = "publishedAt")
	val publishedAt: String? = null,

	@field:SerializedName("content")
	@ColumnInfo(name = "content")
	val content: String? = null
){
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Int? = null
}