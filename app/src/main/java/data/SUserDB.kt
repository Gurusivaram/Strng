package data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SUserEntity::class], version = 1, exportSchema = false)
@TypeConverters(SContactsTypeConverter::class)
abstract class SUserDB : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: SUserDB? = null

        fun getDatabase(context: Context): SUserDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SUserDB::class.java,
                    "DB"
                ).build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }

    abstract fun userDao(): SUserDao?
}