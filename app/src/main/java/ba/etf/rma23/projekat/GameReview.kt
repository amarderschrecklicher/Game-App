package ba.etf.rma23.projekat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview(
    @PrimaryKey val id : Int?,
    @ColumnInfo("review") @SerializedName("review") var review: String?,
    @ColumnInfo("igdb_id") @SerializedName("Gameid") val gameId: Int?,
    @ColumnInfo("online") var online : Boolean = false,
    @ColumnInfo("rating") @SerializedName("rating") var rating: String?,
    @SerializedName("timestamp") val timestamp: String
)
