package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma23.projekat.GameReview

@androidx.room.Database(entities = arrayOf(GameReview::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun reviewDAO(): GameReviewDao

    companion object {
        private var INSTANCE: Database? = null
        fun getInstance(context: Context): Database {
            if (INSTANCE == null) {
                synchronized(Database::class) {
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }
        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "Game-db"
            ).build()
    }
}