package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import ba.etf.rma23.projekat.Game
import ba.etf.rma23.projekat.HomeFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object GamesRepository {
     @SuppressLint("SuspiciousIndentation")
     suspend fun getGamesByName(name:String):List<Game>{
         return withContext(Dispatchers.IO) {
             val response = IGDBApiConfig.ApiAdapter.retrofit.getGames(IGDBApiConfig.FIELDS + "search \"$name\";limit 10;")
             for (game in response.body()!!) {
                 if (game.ageRatings != null && game.ageRatings.isNotEmpty()) {
                     val firstAgeRating = game.ageRatings[0]
                     if (firstAgeRating.category == 2 || firstAgeRating.category == 1) {
                         game.esrbRating = firstAgeRating.rating.toString()
                     }
                 }
                 game.platforms?.let{platforms->
                     if(platforms.isNotEmpty()){
                         game.platform=""
                         for(plat in platforms)
                         game.platform += plat.name + ", "
                     }
                 }
                 game.platform = game.platform?.dropLast(2)
                 game.developer = game.companies?.get(0)?.company?.name
                 game.releaseDate = game.releaseDates?.get(0)?.human
                 game.genre = game.genres?.get(0)?.name
                 game.attributesImage = game.imageUrl?.url

                 for(fav in AccountApiConfig.favoriteGames!!){
                     for(game in response.body()!!)
                         if(game.id == fav.id)
                             game.favorite = true
                 }
             }
             HomeFragment.gamesList = response.body()
             return@withContext response.body()!!
         }
    }

     suspend fun getGamesSafe(name:String):List<Game>{
         return withContext(Dispatchers.IO) {
         val response = IGDBApiConfig.ApiAdapter.retrofit.getGames(IGDBApiConfig.SAFE_FIELDS + "search \"$name\";limit 10;")

         HomeFragment.gamesList = response.body()
         return@withContext response.body()!!
    }
     }

     fun sortGames():List<Game>{
            return  HomeFragment.gamesList!!.filter { it.favorite } + HomeFragment.gamesList!!.filter { !it.favorite}
    }

}