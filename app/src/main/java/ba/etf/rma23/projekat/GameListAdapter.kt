package ba.etf.rma23.projekat

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.provider.CalendarContract.Colors
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig
import ba.etf.rma23.projekat.data.repositories.AccountGamesRepository
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GameListAdapter (
    private var games: List<Game>,
    private val onItemClicked: (game: Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }
    override fun getItemCount(): Int = games.size
    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gameTitle.text = games[position].name
        holder.itemView.setOnClickListener{ onItemClicked(games[position]) }
        holder.gameRating.text = games[position].rating.toString().substring(0,4)
        holder.gameDate.text = games[position]?.releaseDate
        holder.gamePlatform.text = games[position]?.platform
        val fav = holder.addFavButton

        if(games[position].favorite)
        {
            fav.setBackgroundColor(Color.MAGENTA)
            fav.text = "Remove from Fav"
        }
        else {
            fav.setBackgroundColor(Color.CYAN)
            fav.text = "Add to Fav"
        }

        holder.addFavButton.setOnClickListener {
            if(fav.text == "Remove from Fav")
            {
                fav.text = "Add to Fav"
                fav.setBackgroundColor(Color.CYAN)
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch{
                    AccountGamesRepository.removeGame(games[position].id)
                }
                }
            else {
                fav.text = "Remove from Fav"
                fav.setBackgroundColor(Color.MAGENTA)
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch{
                    AccountGamesRepository.saveGame(games[position])
                }
            }
            games
        }

        val context = holder.gameLogo.context
        Glide.with(context).load("https:" + games[position]?.attributesImage).into(holder.gameLogo)
    }
    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }
    inner class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gameRating: TextView = itemView.findViewById(R.id.game_rating_textview)
        val gameTitle: TextView = itemView.findViewById(R.id.item_title_textview)
        val gameDate: TextView = itemView.findViewById(R.id.release_date)
        val gamePlatform: TextView = itemView.findViewById(R.id.game_platform_textview)
        val gameLogo:ImageView = itemView.findViewById(R.id.gameLogo)
        val addFavButton: Button = itemView.findViewById(R.id.addFavButton)
    }
}