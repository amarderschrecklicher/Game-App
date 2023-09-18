package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.Game
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class IGDBApiConfig {

    companion object {
        const val FIELDS =
            "id,name,release_dates.human,age_ratings.rating,age_ratings.category,platforms.name,involved_companies.company.name,rating,genres.name,cover.url,summary;"
        const val SAFE_FIELDS = FIELDS + "where age_ratings.category = 2;where age_ratings.rating < 5;"
        const  val clientID = "u2a8d26fa2kq69sopf721alexuo3a5"
        const val accessToken = "Bearer d49ioq4un55pv7qdhsfxjvla567pd6"
    }

    interface Api {

        @Headers(
            "Client-ID: $clientID",
            "Authorization: $accessToken",
            "Accept: application/json")
        @POST("games")
        suspend fun getGames(
            @Query("fields") fields :String
        ): Response<List<Game>>

    }

    object ApiAdapter {
        val retrofit : Api = Retrofit.Builder()
            .baseUrl("https://api.igdb.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }


}