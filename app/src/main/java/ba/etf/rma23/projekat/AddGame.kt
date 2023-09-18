package ba.etf.rma23.projekat

import com.google.gson.annotations.SerializedName

data class AddGame(
    @SerializedName("igdb_id") val igdbID: Int?,
    @SerializedName("name") val name: String?
)
