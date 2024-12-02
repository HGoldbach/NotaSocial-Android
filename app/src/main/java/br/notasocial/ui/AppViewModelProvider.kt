package br.notasocial.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.notasocial.NotaSocialApplication
import br.notasocial.ui.viewmodel.CatalogViewModel
import br.notasocial.ui.viewmodel.MainViewModel
import br.notasocial.ui.viewmodel.QrCodeResultViewModel
import br.notasocial.ui.viewmodel.QrCodeViewModel
import br.notasocial.ui.viewmodel.buscarproduto.SearchProductViewModel
import br.notasocial.ui.viewmodel.customer.home.HomeViewModel
import br.notasocial.ui.viewmodel.customer.signin.SignInViewModel
import br.notasocial.ui.viewmodel.customer.product.ProductViewModel
import br.notasocial.ui.viewmodel.customer.profile.ProfileViewModel
import br.notasocial.ui.viewmodel.customer.ranking.RankingViewModel
import br.notasocial.ui.viewmodel.customer.register.SignUpViewModel
import br.notasocial.ui.viewmodel.customer.searchstore.SearchStoreViewModel
import br.notasocial.ui.viewmodel.customer.shoplist.ShopListViewModel
import br.notasocial.ui.viewmodel.customer.storeprofile.StoreProfileViewModel
import br.notasocial.ui.viewmodel.customer.storepromotion.StorePromotionViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.FavoritesViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.FollowersViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.FollowingViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.NotificationsViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.ReceiptsViewModel
import br.notasocial.ui.viewmodel.customer.userprofile.UserProfileViewModel
import br.notasocial.ui.viewmodel.store.AddressViewModel
import br.notasocial.ui.viewmodel.store.PromotionDetailsViewModel
import br.notasocial.ui.viewmodel.store.PromotionViewModel
import br.notasocial.ui.viewmodel.store.StoreHomeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            MainViewModel(notaSocialApplication().userPreferencesRepository)
        }
        initializer {
            QrCodeViewModel(
                notaSocialApplication().container.notaSocialApiRepository
            )
        }
        initializer {
            CatalogViewModel(
                notaSocialApplication().container.notaSocialApiRepository
            )
        }
        initializer {
            QrCodeResultViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.notaSocialApiRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            SearchProductViewModel(
                notaSocialApplication().container.notaSocialApiRepository,
                notaSocialApplication().container.productApiRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            SignInViewModel(
                notaSocialApplication().container.authApiRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            SearchStoreViewModel(
                notaSocialApplication().container.storeApiRepository
            )
        }
        initializer {
            HomeViewModel(
                notaSocialApplication().container.productApiRepository,
                notaSocialApplication().userPreferencesRepository,
            )
        }
        initializer {
            FavoritesViewModel(
                notaSocialApplication().container.productApiRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            NotificationsViewModel()
        }
        initializer {
            ShopListViewModel(
                notaSocialApplication().container.productApiRepository,
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().userPreferencesRepository,
            )
        }
        initializer {
            ProfileViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().userPreferencesRepository,
                notaSocialApplication().container.notaSocialApiRepository
            )
        }
        initializer {
            ProductViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.productApiRepository,
                notaSocialApplication().userPreferencesRepository,
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().container.storeApiRepository
            )
        }
        initializer {
            SignUpViewModel(
                notaSocialApplication().container.authApiRepository
            )
        }
        initializer {
            AddressViewModel(
                notaSocialApplication().container.storeDbRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            PromotionViewModel(
                notaSocialApplication().container.storeDbRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            UserProfileViewModel(
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().userPreferencesRepository,
                notaSocialApplication().container.authApiRepository
            )
        }
        initializer {
            FollowersViewModel(
                notaSocialApplication().userPreferencesRepository,
                notaSocialApplication().container.userApiRepository
            )

        }
        initializer {
            FollowingViewModel(
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().userPreferencesRepository
            )
        }
        initializer {
            ReceiptsViewModel(
                notaSocialApplication().container.notaSocialApiRepository,
                notaSocialApplication().container.userApiRepository,
                notaSocialApplication().userPreferencesRepository,
            )
        }
        initializer {
            RankingViewModel(
                notaSocialApplication().container.userApiRepository
            )
        }
        initializer {
            StoreHomeViewModel(
                notaSocialApplication().container.storeApiRepository,
                notaSocialApplication().userPreferencesRepository,
                notaSocialApplication().container.storeDbRepository
            )
        }
        initializer {
            PromotionDetailsViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.storeDbRepository,
            )
        }
        initializer {
            StoreProfileViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.storeApiRepository,
                notaSocialApplication().container.storeDbRepository
            )
        }
        initializer {
            StorePromotionViewModel(
                this.createSavedStateHandle(),
                notaSocialApplication().container.storeDbRepository
            )
        }

    }

}

fun CreationExtras.notaSocialApplication(): NotaSocialApplication =
    (this[APPLICATION_KEY] as NotaSocialApplication)