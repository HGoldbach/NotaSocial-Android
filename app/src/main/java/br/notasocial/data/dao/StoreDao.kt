package br.notasocial.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.notasocial.data.model.StoreDb.StoreDb

@Dao
interface StoreDao {
    @Query("SELECT * FROM store WHERE id = :id")
    suspend fun getStoreById(id: Int): StoreDb?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStore(store: StoreDb)
}