package com.dicoding.skivent.dataclass

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("history")
	val history: List<List<HistoryItemItem>>
)

data class HistoryItemItem(

	@field:SerializedName("treatment")
	val treatment: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("prediction_id")
	val predictionId: String,

	@field:SerializedName("image_url")
	val imageUrl: String,

	@field:SerializedName("confidence_score")
	val confidenceScore: Any,

	@field:SerializedName("predicted_class")
	val predictedClass: Int,

	@field:SerializedName("created_at")
	val createdAt: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("predicted_disease")
	val predictedDisease: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("prevention")
	val prevention: String
)
