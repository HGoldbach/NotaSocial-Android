package br.notasocial.ui.view.consumidor.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.components.InputField
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.consumidor.register.ProfileType
import br.notasocial.ui.viewmodel.consumidor.register.SignUpUiState
import br.notasocial.ui.viewmodel.consumidor.register.SignUpViewModel

object SignUpScreenDestination : NavigationDestination {
    override val route = "signup"
    override val title = "Registrar"
}

@Composable
fun SignUpScreen(
    navigateToLogin: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SignUpViewModel.UiEvent.RegistrationSuccess -> {
                    Toast.makeText(context, "Registro realizado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    navigateToLogin()
                }

                is SignUpViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(128f, .52f, .47f, 1f))
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopSection(
            navigateUp = navigateUp
        )
        Spacer(modifier = Modifier.height(40.dp))
        FormSection(
            modifier = Modifier.weight(1f),
            uiState = uiState,
            onPerfilChange = viewModel::onProfileTypeChange,
            onNameChange = viewModel::onNameChange,
            onEmailChange = viewModel::onEmailChange,
            onCnpjChange = viewModel::onCnpjChange,
            onPasswordChange = viewModel::onPasswordChange,
            onConfirmPasswordChange = viewModel::onConfirmPasswordChange,
            onRegisterClick = viewModel::register
        )
        BottomSection(navigateToLogin)
    }
}

@Composable
fun TopSection(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(
                onClick = { navigateUp() },
                modifier = Modifier.size(25.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.left_arrow),
                    contentDescription = stringResource(id = R.string.back_icon_description),
                    tint = Color.White
                )
            }
        }
        Text(
            text = stringResource(id = R.string.welcome_title),
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Text(
            text = stringResource(id = R.string.create_account),
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun FormSection(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onPerfilChange: (ProfileType) -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCnpjChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    Text(
        text = stringResource(R.string.profile),
        color = Color.White,
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    ) {
        Button(
            onClick = { onPerfilChange(ProfileType.USUARIO) },
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.profileType == ProfileType.USUARIO) Color.White else Color.hsl(
                    0f, 0f, 1f, .50f
                )
            )
        ) {
            Text(
                text = stringResource(R.string.user),
                color = if (uiState.profileType == ProfileType.USUARIO) Color.hsl(
                    123f, .63f, .33f, 1f
                ) else Color.White,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
        Button(
            onClick = { onPerfilChange(ProfileType.ESTABELECIMENTO) },
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (uiState.profileType == ProfileType.ESTABELECIMENTO) Color.White else Color.hsl(
                    0f, 0f, 1f, .50f
                )
            )
        ) {
            Text(
                text = stringResource(R.string.store),
                color = if (uiState.profileType == ProfileType.ESTABELECIMENTO) Color.hsl(
                    123f, .63f, .33f, 1f
                ) else Color.White,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InputField(
            value = uiState.name,
            onValueChange = onNameChange,
            placeholder = "NOME",
            label = "NOME",
            icon = Icons.Default.Person,
            isError = !uiState.isNameValid
        )
        if (uiState.profileType == ProfileType.ESTABELECIMENTO) {
            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                value = uiState.cnpj,
                onValueChange = onCnpjChange,
                placeholder = "CNPJ",
                label = "CNPJ",
                icon = Icons.Default.AccountBox,
                isError = !uiState.isCnpjValid // Supondo validação de CNPJ no estado
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = "EMAIL",
            label = "EMAIL",
            icon = Icons.Default.Email,
            isError = !uiState.isEmailValid
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = uiState.password,
            onValueChange = onPasswordChange,
            placeholder = "SENHA",
            label = "SENHA",
            icon = Icons.Default.Lock,
            isError = !uiState.isPasswordValid
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = uiState.confirmPassword,
            onValueChange = onConfirmPasswordChange,
            placeholder = "CONFIRMAR SENHA",
            label = "CONFIRMAR SENHA",
            icon = Icons.Default.Lock,
            isError = !uiState.isConfirmPasswordValid
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text(
                text = "Registrar",
                color = Color.hsl(123f, .63f, .33f, 1f),
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun BottomSection(
    navigateToLogin: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.has_an_account),
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        )
        TextButton(
            onClick = { navigateToLogin() },
            content = {
                Text(
                    text = stringResource(R.string.getin),
                    color = Color.White,
                    fontFamily = ralewayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrarScreenPreview() {
    NotasocialTheme {
        SignUpScreen(
            {}, {}
        )
    }
}