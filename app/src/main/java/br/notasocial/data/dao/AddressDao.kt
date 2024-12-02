package br.notasocial.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.notasocial.data.model.StoreDb.AddressDb

@Dao
interface AddressDao {
    @Query("SELECT * FROM address WHERE store_id = :storeId")
    suspend fun getAddressesByStoreId(storeId: String): List<AddressDb>

    @Query("DELETE FROM address WHERE id = :addressId")
    suspend fun removeAddress(addressId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: AddressDb)


}