package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentProvider
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import ba.etf.rma23.projekat.HomeFragment.Companion.gameToShowDetails
import ba.etf.rma23.projekat.data.repositories.Database
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameDetailsFragment : Fragment() {
    private lateinit var game : Game
    private lateinit var gameTitle : TextView
    private lateinit var gameRating : TextView
    private lateinit var gameGenre : TextView
    private lateinit var gamePlatform : TextView
    private lateinit var gameDate : TextView
    private lateinit var gameDeveloper : TextView
    private lateinit var gamePublisher : TextView
    private lateinit var gameDescription : TextView
    private lateinit var gameLogo : ImageView
    private lateinit var reviewsList : RecyclerView
    private lateinit var reviewsAdapter : CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_game_details, container, false)


        reviewsList = view.findViewById(R.id.recyclerView)
        reviewsList.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        gameTitle = view.findViewById(R.id.item_title_textview)
        gameRating = view.findViewById(R.id.esrb_rating_textview)
        gameGenre = view.findViewById(R.id.genre_textview)
        gameDeveloper = view.findViewById(R.id.developer_textview)
        gameDate = view.findViewById(R.id.release_date2)
        gamePlatform = view.findViewById(R.id.platform_textview)
        gameDescription = view.findViewById(R.id.description_textview)
        gamePublisher = view.findViewById(R.id.publisher_textview)
        gameLogo = view.findViewById(R.id.cover_imageview)


        populateDetails()

        return view;
    }

      @SuppressLint("SuspiciousIndentation")
      fun populateDetails() {
        getReviews(gameToShowDetails!!)
        game = gameToShowDetails!!
        gameTitle.text=game.name
        gameDate.text= game?.releaseDate
        gameGenre.text=game?.genre
        gameRating.text= game?.esrbRating
        gamePlatform.text = game?.platform
        gameDeveloper.text = game?.developer
        gamePublisher.text = game?.developer
        gameDescription.text = game?.description
        reviewsAdapter = CommentsAdapter(arrayListOf())
        reviewsList.adapter = reviewsAdapter
        game.userImpressions?.let { reviewsAdapter.updateImpression(it) }
        gameToShowDetails = game
        val context: Context = gameLogo.context
          Glide.with(context).load("https:" + game.attributesImage!!).into(gameLogo)

}
    private fun getReviews(game : Game) {
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val reviews = GameReviewsRepository.getReviewsForGame(game.id)
            var list: ArrayList<UserImpression> = ArrayList()
                for (review in reviews) {
                    if (review.rating != null) {
                        list.add(UserRating(review.id.toString(),review.timestamp.toLong(),
                            review.rating!!.toDouble() * 1.0))
                    }
                    if (review.review != null) {
                        list.add(UserReview(review.id.toString(), review.timestamp.toLong(),
                            review.review!!
                        ))
                    }
                }
            gameToShowDetails!!.userImpressions = list
            }
        }

}