package br.notasocial.data.model.StoreDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "store")
data class StoreDb(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "cnpj")
    val cnpj: String
)

@Entity(
    tableName = "promotion",
    foreignKeys = [ForeignKey(
        entity = StoreDb::class,
        parentColumns = ["id"],
        childColumns = ["store_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["store_id"])]
)
data class PromotionDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "store_id")
    val storeId: String = "", // Foreign key para StoreDb
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "validity")
    val validity: String = "",
    @ColumnInfo(name = "establishment")
    val establishment: String = "",
    @ColumnInfo(name = "products")
    val products: String = "" // Serializado com TypeConverter
)

@Entity(
    tableName = "address",
    foreignKeys = [ForeignKey(
        entity = StoreDb::class,
        parentColumns = ["id"],
        childColumns = ["store_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["store_id"])]
)
data class AddressDb(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "store_id")
    val storeId: String,
    @ColumnInfo(name = "street")
    val street: String,
    @ColumnInfo(name = "number")
    val number: String,
    @ColumnInfo(name = "complement")
    val complement: String? = null,
    @ColumnInfo(name = "district")
    val district: String,
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "state")
    val state: String,
    @ColumnInfo(name = "zip_code")
    val zipCode: String,
    @ColumnInfo(name = "phone")
    val phone: String
)

data class Product(
    val name: String,
    val price: Double
)