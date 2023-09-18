package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class AccountApiConfig {
    companion object {
        var accountHash = "f14630af-7476-4678-98d7-546f9404fff2"
        var userAge : Int = 80
        var favoriteGames : ArrayList<Game>? = ArrayList()
    }

    interface Api {
        @POST("account/{accountHash}/game")
        suspend fun addGame(@Path("accountHash") accountHash: String, @Body game: FavoriteGame):Response<AddGame>

        @GET("account/{accountHash}/games")
        suspend fun getGames(@Path("accountHash") accountHash: String): Response<List<AddGame>>

        @DELETE("account/{accountHash}/game/{gid}/")
        suspend fun deleteGame(@Path("gid",) gameId: Int,@Path("accountHash") accountHash: String): Response<Game>

        @DELETE("account/{accountHash}/game")
        suspend fun deleteGames(@Path("accountHash") accountHash: String): Response<Boolean>

    }

    object ApiAdapter {
        val retrofit : Api = Retrofit.Builder()
            .baseUrl("https://rma23ws.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}