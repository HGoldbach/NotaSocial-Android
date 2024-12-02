package br.notasocial.data.database

import androidx.room.TypeConverter
import br.notasocial.data.model.StoreDb.AddressDb
import br.notasocial.data.model.StoreDb.Product
import br.notasocial.data.model.StoreDb.PromotionDb
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromProductList(products: List<Product>): String {
        return gson.toJson(products)
    }

    @TypeConverter
    fun toProductList(productsString: String): List<Product> {
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(productsString, type)
    }
}