package br.notasocial.data.repository

import br.notasocial.data.dao.AddressDao
import br.notasocial.data.dao.PromotionDao
import br.notasocial.data.dao.StoreDao
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.PromotionDb
import br.notasocial.data.model.StoreDb.StoreDb

interface StoreDbRepository {
    // Métodos para Store
    suspend fun getStoreById(id: Int): StoreDb
    suspend fun insertStore(store: StoreDb)

    // Métodos para Promotion
    suspend fun getPromotionsByStoreId(storeId: String): List<PromotionDb>
    suspend fun getPromotionById(promotionId: Int): PromotionDb
    suspend fun insertPromotion(promotion: PromotionDb)
    suspend fun deletePromotion(promotionId: Int)

    // Métodos para Address
    suspend fun getAddressesByStoreId(storeId: String): List<AddressDb>
    suspend fun insertAddress(address: AddressDb)
    suspend fun removeAddress(addressId: Int)

}


class OfflineStoreDbRepository(
    private val storeDao: StoreDao,
    private val promotionDao: PromotionDao,
    private val addressDao: AddressDao
) : StoreDbRepository {
    // Store
    override suspend fun getStoreById(id: Int): StoreDb {
        return storeDao.getStoreById(id)!!
    }

    override suspend fun insertStore(store: StoreDb) {
        storeDao.insertStore(store)
    }

    // Promotion
    override suspend fun getPromotionsByStoreId(storeId: String): List<PromotionDb> {
        return promotionDao.getPromotionsByStoreId(storeId)
    }

    override suspend fun getPromotionById(promotionId: Int): PromotionDb {
        return promotionDao.getPromotionById(promotionId)
    }

    override suspend fun insertPromotion(promotion: PromotionDb) {
        promotionDao.insertPromotion(promotion)
    }

    override suspend fun deletePromotion(promotionId: Int) {
        promotionDao.removePromotion(promotionId)
    }

    // Address
    override suspend fun getAddressesByStoreId(storeId: String): List<AddressDb> {
        return addressDao.getAddressesByStoreId(storeId)
    }

    override suspend fun insertAddress(address: AddressDb) {
        addressDao.insertAddress(address)
    }

    override suspend fun removeAddress(addressId: Int) {
        addressDao.removeAddress(addressId)
    }
}
