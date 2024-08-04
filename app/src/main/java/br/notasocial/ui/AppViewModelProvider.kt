package br.notasocial.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import br.notasocial.NotaSocialApplication
import br.notasocial.ui.viewmodel.CatalogViewModel
import br.notasocial.ui.viewmodel.QrCodeResultViewModel
import br.notasocial.ui.viewmodel.QrCodeViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
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
                notaSocialApplication().container.notaSocialApiRepository
            )
        }
    }

}

fun CreationExtras.notaSocialApplication(): NotaSocialApplication =
    (this[APPLICATION_KEY] as NotaSocialApplication)