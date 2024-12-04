package br.notasocial.ui.view.customer.userprofile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.data.model.User.UserResponse
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.store.InputField
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.userprofile.UserProfileViewModel

object AccountDestination : NavigationDestination {
    override val route = "userprofile_account"
    override val title = "Conta"
}

@Composable
fun AccountScreen(
    modifier: Modifier = Modifier,
    navigateToUserProfile: () -> Unit,
    viewModel: UserProfileViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.uiState

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UserProfileViewModel.UiEvent.AccountChangeSuccess -> {
                    Toast.makeText(
                        context,
                        "Informações alteradas com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToUserProfile()
                }

                is UserProfileViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, .97f, 1f))
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            HomeTopSection(
                user = viewModel.user
            )
            AccountFormSection(
                user = viewModel.user,
                firstNameValue = uiState.firstName,
                lastNameValue = uiState.lastName,
                emailValue = uiState.email,
                passwordValue = uiState.password,
                confirmPasswordValue = uiState.confirmPassword,
                onFirstNameChange = viewModel::onFirstNameChange,
                onLastNameChange = viewModel::onLastNameChange,
                onPasswordChange = viewModel::onPasswordChange,
                onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
                onSave = viewModel::updateUserProfile
            )
        }
    }
}

@Composable
fun AccountFormSection(
    firstNameValue: String,
    lastNameValue: String,
    emailValue: String,
    passwordValue: String,
    confirmPasswordValue: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
    user: UserResponse
) {
    var mudarSenha by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        InputField(
            text = "Nome",
            value = firstNameValue,
            placeholder = user.firstName,
            onValueChange = onFirstNameChange,
            modifier = Modifier.padding(vertical = 12.dp)
        )
        InputField(
            text = "Sobrenome",
            value = lastNameValue,
            placeholder = user.lastName,
            onValueChange = onLastNameChange,
            modifier = Modifier.padding(vertical = 5.dp)
        )
        InputField(
            text = "Email",
            value = emailValue,
            enabled = false,
            placeholder = user.email,
            onValueChange = {},
            modifier = Modifier.padding(vertical = 5.dp)
        )
        Row(
            modifier = Modifier.padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mudar Senha",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ralewayFamily,
                modifier = Modifier.padding(end = 20.dp)
            )
            Icon(
                painter = painterResource(
                    id =
                    if (mudarSenha) {
                        R.drawable.angle_up_solid
                    } else {
                        R.drawable.angle_down_solid
                    }
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(14.dp)
                    .clickable {
                        mudarSenha = !mudarSenha
                    }
            )
        }
        if (mudarSenha) {
            InputField(
                text = "Senha",
                value = passwordValue,
                onValueChange = onPasswordChange,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            InputField(
                text = "Confirmar Senha",
                value = confirmPasswordValue,
                onValueChange = onConfirmPasswordChange
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = onSave,
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.hsl(123f, .63f, .33f, 1f),
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .width(120.dp)
                    .height(45.dp)
            ) {
                Text(
                    text = "Salvar".uppercase(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = ralewayFamily
                )
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    NotasocialTheme {
        AccountScreen(
            navigateToUserProfile = {},
        )
    }
}