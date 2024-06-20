package com.dicoding.skivent.dataclass

import com.google.gson.annotations.SerializedName

data class DetectionResponse(

	@field:SerializedName("result")
	val result: Result?,

	@field:SerializedName("success")
	val success: Boolean
)

data class Result(

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("confidenceScore")
	val confidenceScore: Any,

	@field:SerializedName("predictedClass")
	val predictedClass: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("predictedDisease")
	val predictedDisease: String,

	@field:SerializedName("prevention")
	val prevention: String
)
