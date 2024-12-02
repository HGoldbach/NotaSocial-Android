package br.notasocial.data.dao

import androidx.room.Dao
import androidx.room.Delete
import br.notasocial.data.model.StoreDb.PromotionDb
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PromotionDao {
    @Query("SELECT * FROM promotion WHERE store_id = :storeId")
    suspend fun getPromotionsByStoreId(storeId: String): List<PromotionDb>

    @Query("SELECT * FROM promotion WHERE id = :promotionId")
    suspend fun getPromotionById(promotionId: Int): PromotionDb

    @Query("DELETE FROM promotion WHERE id = :promotionId")
    suspend fun removePromotion(promotionId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromotion(promotion: PromotionDb)
}
