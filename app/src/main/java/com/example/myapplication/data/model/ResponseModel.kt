package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("totalResults")
	val totalResults: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesModel?>? = null
)