package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import ba.etf.rma23.projekat.data.repositories.Database
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import ba.etf.rma23.projekat.data.repositories.GamesRepository
import kotlinx.coroutines.*

open class HomeFragment : Fragment() {

    companion object{
        var gameToShowDetails: Game? =  null
        var gamesList: List<Game>? = emptyList()
    }
    private lateinit var searchText : TextView
    private lateinit var searchButton : Button
    private lateinit var favoriteBox : CheckBox
    private lateinit var sortBox : CheckBox
    private lateinit var allGames: RecyclerView
    private lateinit var gamesAdapter: GameListAdapter


     @SuppressLint("SuspiciousIndentation")
     override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_main, container, false)
        allGames = view.findViewById(R.id.game_list)
        allGames.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )

         searchButton = view.findViewById(R.id.search_button)
         favoriteBox = view.findViewById(R.id.favoriteBox)
         sortBox = view.findViewById(R.id.sortBox)
         searchText = view.findViewById(R.id.search_query_edittext)
         gamesAdapter = GameListAdapter(arrayListOf()) { game -> showGameDetails(game) }
         allGames.adapter=gamesAdapter

         if(hasInternetConnection(requireContext())) {
             getFavorites()
         }

        favoriteBox.setOnCheckedChangeListener {
                _, isChecked ->
                if(isChecked) onFavoriteClick()
                 else {
                    gamesList = emptyList()
                    gamesAdapter.updateGames(gamesList!!)
                 }
        }
        searchButton.setOnClickListener{
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                favoriteBox.isChecked = false
                sortBox.isChecked = false
                onSearchClick()
            }
        }
         sortBox.setOnCheckedChangeListener { _, isChecked ->
             if(isChecked)
                 gamesAdapter.updateGames(sortGames())
             else
                 gamesAdapter.updateGames(gamesList!!)
         }

        return view;
    }


    private fun showGameDetails(game: Game) {
        gameToShowDetails = game
        val listener = context as DataListener
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            listener.showDetails()
         else listener.refreshDetails(game)
    }

    private fun onFavoriteClick() {
        gamesList = AccountApiConfig.favoriteGames
        gamesAdapter.updateGames(gamesList!!)
    }

    private  fun onSearchClick() {

            search(searchText.text.toString());
            gamesAdapter.updateGames(gamesList!!)

    }
    private fun searchDone(){
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
    }
    private  fun search(query: String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            when (val result = GamesRepository.getGamesByName(query)) {
                else -> searchDone()
            }
        }
    }
    private fun getFavorites(){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch{
            AccountGamesRepository.getSavedGames()
        }
    }

    private fun sortGames():List<Game>{
         return GamesRepository.sortGames()
    }

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false

    }
}