package br.notasocial.ui.view.customer.signin

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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import br.notasocial.ui.viewmodel.customer.signin.SignInUiState
import br.notasocial.ui.viewmodel.customer.signin.SignInViewModel

object SignInDestination : NavigationDestination {
    override val route = "signin"
    override val title = "Login"
}

@Composable
fun SignInScreen(
    navigateToHome: () -> Unit,
    navigateToRegistrar: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is SignInViewModel.UiEvent.LoginSuccess -> {
                    Toast.makeText(context, "Login realizado com sucesso!", Toast.LENGTH_SHORT)
                        .show()
                    navigateToHome()
                }
                is SignInViewModel.UiEvent.ShowError -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
            .background(color = Color.hsl(128f,.52f,.47f,1f))
            .padding(35.dp)
    ) {
        TopSection(
            navigateUp = navigateUp
        )
        FormSection(
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onSenhaChange = viewModel::onPasswordChange,
            onLogin = { viewModel.login() },
            modifier = Modifier.weight(1f)
        )
        BottomSection(navigateToRegistrar)
    }
}

@Composable
fun TopSection(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { navigateUp() },
            modifier = Modifier.size(25.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.left_arrow),
                contentDescription = "Voltar",
                tint = Color.White
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Bem vindo",
                color = Color.White,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(
                text = "Faça o login em sua conta",
                color = Color.White,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }

    }
}

@Composable
fun FormSection(
    uiState: SignInUiState,
    onLogin: () -> Unit,
    onEmailChange: (String) -> Unit,
    onSenhaChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(top = 50.dp)
    ) {
        InputField(
            value = uiState.email,
            onValueChange = onEmailChange,
            placeholder = "Digite o seu email".uppercase(),
            label = "EMAIL",
            icon = Icons.Default.Email,
            isError = !uiState.isEmailValid
        )
        if (!uiState.isEmailValid) {
            Text(
                text = "*Formato de email inválido",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ralewayFamily,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        InputField(
            value = uiState.password,
            onValueChange = onSenhaChange,
            placeholder = "SENHA",
            label = "SENHA",
            icon = Icons.Default.Lock,
            isError = !uiState.isPasswordValid
        )
        if (!uiState.isPasswordValid) {
            Text(
                text = "*A senha deve ter no mínimo 8 caracteres",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ralewayFamily,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = onLogin,
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "Entrar",
                color = Color.hsl(123f,.63f,.33f,1f),
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun BottomSection(navigateToRegistrar: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Não tem uma conta ainda?",
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        )
        TextButton(onClick = navigateToRegistrar) {
            Text(
                text = "Registrar",
                color = Color.White,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NotasocialTheme {
        SignInScreen(
            {},{},{}
        )
    }
}

