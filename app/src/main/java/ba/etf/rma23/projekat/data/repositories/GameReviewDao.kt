package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma23.projekat.GameReview

@Dao
interface GameReviewDao {
    @Query("SELECT * FROM gamereview")
    suspend fun getAll():List<GameReview>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg reviews : GameReview)

    @Query("UPDATE gamereview SET online=:online WHERE id = :id")
    suspend fun update(online: Boolean?, id: Int)

}