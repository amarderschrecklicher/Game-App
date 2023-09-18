package ba.etf.rma23.projekat

import ba.etf.rma23.projekat.Game
import com.google.gson.annotations.SerializedName

data class  FavoriteGame (
    @SerializedName("game") var game : AddGame
)