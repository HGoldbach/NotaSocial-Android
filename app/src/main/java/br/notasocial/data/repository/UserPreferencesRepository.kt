package br.notasocial.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

data class UserData(val token: String, val role: String, val keycloakId: String, val productsId: List<String>)

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_ROLE = stringPreferencesKey("user_role")
        val USER_KEYCLOAK_ID = stringPreferencesKey("user_keycloak_id")
        val USER_PRODUCTS_ID = stringPreferencesKey("user_products_id")
    }

    val currentUserToken: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_TOKEN] ?: ""
        }

    val currentUserRole: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_ROLE] ?: ""
        }

    val currentUserKeycloakId: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[USER_KEYCLOAK_ID] ?: ""
        }

    val currentUserProductsId: Flow<List<String>> =
        dataStore.data.map { preferences ->
            preferences[USER_PRODUCTS_ID]?.split(",")?.filter { it.isNotEmpty() } ?: emptyList()
        }

    val currentUserData: Flow<UserData> = combine(
        currentUserToken,
        currentUserRole,
        currentUserKeycloakId,
        currentUserProductsId
    ) { token, role, keycloakId, productsId ->
        UserData(token, role, keycloakId, productsId)
    }

    suspend fun saveUserToken(token: String) {
        dataStore.edit { preferences ->
            preferences[USER_TOKEN] = token
        }
    }

    suspend fun saveUserRole(role: String) {
        dataStore.edit { preferences ->
            preferences[USER_ROLE] = role
        }
    }

    suspend fun saveUserKeycloakId(keycloakId: String) {
        dataStore.edit { preferences ->
            preferences[USER_KEYCLOAK_ID] = keycloakId
        }
    }

    suspend fun saveUserProductsId(productsId: String) {
        dataStore.edit { preferences ->
            preferences[USER_PRODUCTS_ID] = productsId
        }
    }

    suspend fun addProductId(productId: String) {
        dataStore.edit { preferences ->
            val currentProducts = preferences[USER_PRODUCTS_ID]?.split(",")?.toMutableList() ?: mutableListOf()
            if (!currentProducts.contains(productId)) {
                currentProducts.add(productId)
                preferences[USER_PRODUCTS_ID] = currentProducts.joinToString(",")
            }
        }
    }

    suspend fun removeProductId(productId: String) {
        dataStore.edit { preferences ->
            val currentProducts = preferences[USER_PRODUCTS_ID]?.split(",")?.toMutableList() ?: mutableListOf()
            if (currentProducts.contains(productId)) {
                currentProducts.remove(productId)
                preferences[USER_PRODUCTS_ID] = currentProducts.joinToString(",")
            }
        }
    }


    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.remove(USER_TOKEN)
            preferences.remove(USER_ROLE)
            preferences.remove(USER_KEYCLOAK_ID)
            preferences.remove(USER_PRODUCTS_ID)
        }
    }

}