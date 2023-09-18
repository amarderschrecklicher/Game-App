package ba.etf.rma23.projekat.data.repositories

import android.annotation.SuppressLint
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import ba.etf.rma23.projekat.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
object GameReviewsRepository {

    suspend fun getOfflineReviews(context : Context):List <GameReview>{
        return withContext(Dispatchers.IO) {
            try {
                val db = Database.getInstance(context)
                val reviews = db.reviewDAO().getAll()
                return@withContext reviews.filter { !it.online }
            } catch (error: Exception) {
                return@withContext emptyList()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun sendOfflineReviews(context: Context):Int{
        return withContext(Dispatchers.IO) {
            var sent = 0
            try {
                val db = Database.getInstance(context)
                val reviews = db.reviewDAO().getAll().filter{!it.online}

                for(review in reviews) {
                    val response = sendReview(context,review)
                    if(response) {
                        sent += 1
                        db.reviewDAO().update(true, review.id!!)
                    }
                }
                return@withContext sent
            } catch (error: Exception) {
                return@withContext sent
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun sendReview( context : Context, review : GameReview):Boolean{

        return withContext(Dispatchers.IO) {
            try {
                var response = false
                for (games in AccountApiConfig.favoriteGames!!) {
                    if (games.id == review.gameId) {
                        response = true
                        break
                    }
                }
                if (!response) {
                    val gamesFav = IGDBApiConfig.ApiAdapter.retrofit.getGames(
                        "${IGDBApiConfig.FIELDS} where id =  ${review.gameId};"
                    )
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
                        AccountGamesRepository.saveGame(game)
                        break
                    }
                }

                if(review.rating=="")
                    review.rating=null
                if(review.review == "")
                    review.review = null
                ReviewsApiConfig.ApiAdapter.retrofit.addReview(
                    AccountApiConfig.accountHash,
                    review.gameId.toString(),
                    AddReview(review.review , review.rating?.toInt())
                )

                return@withContext true

            }
            catch (error: Exception){
                val db = Database.getInstance(context)
                db!!.reviewDAO().insertAll(review)
                return@withContext false
            }
        }
    }

    suspend fun getReviewsForGame(igdb_id :Int):List<GameReview> {
        return withContext(Dispatchers.IO) {
            try {
                val response = ReviewsApiConfig.ApiAdapter.retrofit.getReview(igdb_id).body()
                if(response?.isEmpty() == true)
                    return@withContext emptyList()
                return@withContext response!!
            } catch (error: Exception) {
                return@withContext emptyList()
            }
        }
    }
}