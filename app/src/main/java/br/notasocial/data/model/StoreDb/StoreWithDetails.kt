package br.notasocial.data.model.StoreDb

import androidx.room.Embedded
import androidx.room.Relation

data class StoreWithDetails(
    @Embedded val store: StoreDb,
    @Relation(
        parentColumn = "id",
        entityColumn = "store_id"
    )
    val promotions: List<PromotionDb>,
    @Relation(
        parentColumn = "id",
        entityColumn = "store_id"
    )
    val addresses: List<AddressDb>
)
