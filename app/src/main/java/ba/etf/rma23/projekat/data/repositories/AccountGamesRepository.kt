package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import kotlinx.coroutines.*
import retrofit2.Response

object AccountGamesRepository {

    fun setHash(acHash: String): Boolean {
        AccountApiConfig.accountHash = acHash
        return true
    }

    fun getHash(): String {
        return AccountApiConfig.accountHash
    }

    suspend fun getSavedGames(): List<Game> {
        var fields = ""
        fields = if(AccountApiConfig.userAge>=18) IGDBApiConfig.FIELDS
        else IGDBApiConfig.SAFE_FIELDS
        return withContext(Dispatchers.IO) {
            val games = AccountApiConfig.ApiAdapter.retrofit.getGames(AccountApiConfig.accountHash)
            if (games.body()?.isEmpty() == false) {
                var query = "("
                for (game in games.body()!!) {
                    query += game.igdbID.toString() + ","
                }

                val gamesFav = IGDBApiConfig.ApiAdapter.retrofit.getGames(
                    fields + "where id =" + query.dropLast(1) + ");"
                )
                //this belove
                for (game in gamesFav.body()!!) {
                    if (game.ageRatings != null && game.ageRatings.isNotEmpty()) {
                        val firstAgeRating = game.ageRatings[0]
                        if (firstAgeRating.category == 2 || firstAgeRating.category == 1) {
                            game.esrbRating = firstAgeRating.rating.toString()
                        }
                    }
                    game.platforms?.let { platforms ->
                        if (platforms.isNotEmpty()) {
                            game.platform = ""
                            for (plat in platforms)
                                game.platform += plat.name + ", "
                        }
                    }
                    game.platform = game.platform?.dropLast(2)
                    game.developer = game.companies?.get(0)?.company?.name
                    game.releaseDate = game.releaseDates?.get(0)?.human
                    game.genre = game.genres?.get(0)?.name
                    game.attributesImage = game.imageUrl?.url
                    game.favorite = true
                }

                AccountApiConfig.favoriteGames = gamesFav.body() as ArrayList<Game>?

                for (game in AccountApiConfig.favoriteGames!!) game.favorite
                for (game in gamesFav.body()!!) game.favorite
                return@withContext gamesFav.body()!!
            }
            else return@withContext emptyList()
        }
    }

    suspend fun saveGame(game: Game) {
            game.favorite = true
            var add = true
            AccountApiConfig.favoriteGames?.let{favs->
                if(favs.isNotEmpty()){
                    for(fav in favs)
                        if(fav.id == game.id){
                            add = false
                            break
                        }
                }
            }
                if(add) {
                    AccountApiConfig.ApiAdapter.retrofit.addGame(AccountApiConfig.accountHash,
                        FavoriteGame(AddGame(game.id, game.name))
                    )
                    AccountApiConfig.favoriteGames?.add(game)
                }
    }

    suspend fun removeGame(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            AccountApiConfig.favoriteGames!!.removeIf { it.id == id }
            AccountApiConfig.ApiAdapter.retrofit.deleteGame(id, AccountApiConfig.accountHash)
            return@withContext true
        }
    }

    suspend fun removeNonSafe(): Boolean {
        return withContext(Dispatchers.IO) {
            for (game in AccountApiConfig.favoriteGames!!) {
                if (((game.esrbRating!!.split(" ")[0] == "1") && (game.esrbRating!!.split(" ")[1] == "12")) ||
                    ((game.esrbRating!!.split(" ")[0] == "2") && (game.esrbRating!!.split(" ")[1] == "5"))
                )
                    AccountApiConfig.ApiAdapter.retrofit.deleteGame(
                        game.id,
                        AccountApiConfig.accountHash
                    )
                AccountApiConfig.favoriteGames!!.removeIf { it.id == game.id }
            }
            return@withContext true
        }
    }

    fun getGamesContainingString(query: String): List<Game> {
        return AccountApiConfig.favoriteGames!!.filter { it.name!!.contains(query) }
    }

    fun setAge(age: Int): Boolean {
        AccountApiConfig.userAge = age
        if (age in 1..17)
            return false
        return true
    }

    }
