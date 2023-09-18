package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class Attributes (
    @SerializedName("id")  val id: Int?,
    @SerializedName("url")  val url: String?,
    @SerializedName("name")  val name: String?,
    @SerializedName("human")  val human: String?,
    @SerializedName("category")  val category: Int?,
    @SerializedName("rating")  val rating: Int?,
)