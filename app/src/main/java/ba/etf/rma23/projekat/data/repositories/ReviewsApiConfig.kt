package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.AddGame
import ba.etf.rma23.projekat.AddReview
import ba.etf.rma23.projekat.FavoriteGame
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.GameReview
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class ReviewsApiConfig {

    interface Api {

        @POST("account/{accountHash}/game/{gid}/gamereview")
        suspend fun addReview( @Path("accountHash")accountHash: String,@Path("gid",) gameId: String,@Body review : AddReview) : Response<GameReview>

        @GET("game/{gid}/gamereviews")
        suspend fun getReview(@Path("gid",) gameId: Int): Response<List<GameReview>>

        @DELETE("account/{accountHash}/gamereviews")
        suspend fun deleteReview(@Path("accountHash") accountHash: String): Response<GameReview>

    }

    object ApiAdapter {
        val retrofit : Api = Retrofit.Builder()
            .baseUrl("https://rma23ws.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}

