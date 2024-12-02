package br.notasocial.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.notasocial.data.dao.AddressDao
import br.notasocial.data.dao.PromotionDao
import br.notasocial.data.dao.StoreDao
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.data.model.StoreDb.StoreDb

@Database(
    entities = [StoreDb::class, PromotionDb::class, AddressDb::class],
    version = 5
)
@TypeConverters(Converters::class)
abstract class StoreDatabase : RoomDatabase() {

    abstract fun storeDao(): StoreDao
    abstract fun promotionDao(): PromotionDao
    abstract fun addressDao(): AddressDao

    companion object {
        @Volatile
        private var Instance: StoreDatabase? = null

        fun getDatabase(context: Context): StoreDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, StoreDatabase::class.java, "store_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
