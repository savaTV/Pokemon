import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.poki.Pokemon
import com.example.poki.PokemonDao

@Database(entities = [Pokemon::class], version = 1, exportSchema = false)
@TypeConverters(com.example.poki.TypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "poke_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}