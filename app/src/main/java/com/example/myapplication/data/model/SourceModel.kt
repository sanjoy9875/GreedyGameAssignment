package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class SourceModel(

	@field:SerializedName("id")
	val id: Any? = null,

	@field:SerializedName("name")
    var name: String? = null
)