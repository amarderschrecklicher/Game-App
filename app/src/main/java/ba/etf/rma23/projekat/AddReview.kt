package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class AddReview(
    @SerializedName("review") val review: String?,
    @SerializedName("rating") val rating: Int?
)
