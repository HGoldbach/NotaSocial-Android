package br.notasocial.ui.view.consumidor.registar

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.components.InputField
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.ralewayFamily


enum class Perfil {
    USUARIO, ESTABELECIMENTO
}

object RegistrarScreenDestination : NavigationDestination {
    override val route = "registrar"
    override val title = "Registrar"
}

@Composable
fun RegistrarScreen(
    navigateToLogin: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.hsl(128f,.52f,.47f,1f))
            .padding(35.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TopSection(
            navigateUp = navigateUp
        )
        Spacer(modifier = Modifier.height(40.dp))
        FormSection(modifier = Modifier.weight(1f))
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
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
        }
        Text(
            text = "Bem vindo",
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
        Text(
            text = "Crie uma conta",
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
) {
    var perfil by remember {
        mutableStateOf(Perfil.USUARIO)
    }

    var mostrarCampoExtra by remember {
        mutableStateOf(false)
    }

    Text(
        text = "Perfil",
        color = Color.White,
        fontFamily = ralewayFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp)
    ) {
        Button(
            onClick = {
                perfil = Perfil.USUARIO
                mostrarCampoExtra = false
            },
            modifier = Modifier.weight(1f).height(50.dp).padding(end = 5.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (perfil == Perfil.USUARIO) {
                    Color.White
                } else {
                    Color.hsl (0f,0f,1f,.50f)
                }
            )
        ) {
            Text(
                text = "Usuário",
                color = if (perfil == Perfil.USUARIO) {
                    Color.hsl(123f,.63f,.33f,1f)
                } else {
                    Color.White
                },
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
        Button(
            onClick = {
                perfil = Perfil.ESTABELECIMENTO
                mostrarCampoExtra = true
            },
            modifier = Modifier.weight(1f).height(50.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if(perfil == Perfil.ESTABELECIMENTO) {
                    Color.White
                } else {
                    Color.hsl (0f,0f,1f,.50f)
                }
            )
        ) {
            Text(
                text = "Estabelecimento",
                color = if (perfil == Perfil.ESTABELECIMENTO) {
                    Color.hsl(123f,.63f,.33f,1f)
                } else {
                    Color.White
                },
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputField(
            value = "",
            onValueChange = {},
            placeholder = "NOME",
            label = "NOME",
            icon = Icons.Default.Person
        )
        if (mostrarCampoExtra) {
            Spacer(modifier = Modifier.height(15.dp))
            InputField(
                value = "",
                onValueChange = {},
                placeholder = "CNPJ",
                label = "CNPJ",
                icon = Icons.Default.AccountBox
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = "",
            onValueChange = {},
            placeholder = "EMAIL",
            label = "EMAIL",
            icon = Icons.Default.Email
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = "",
            onValueChange = {},
            placeholder = "SENHA",
            label = "SENHA",
            icon = Icons.Default.Lock
        )
        Spacer(modifier = Modifier.height(15.dp))
        InputField(
            value = "",
            onValueChange = {},
            placeholder = "CONFIRMAR SENHA",
            label = "CONFIRMAR SENHA",
            icon = Icons.Default.Lock
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(55.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White
            )
        ) {
            Text(
                text = "Registrar",
                color = Color.hsl(123f,.63f,.33f,1f),
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
            text = "Já possui uma conta?",
            color = Color.White,
            fontFamily = ralewayFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
        )
        TextButton(
            onClick = { navigateToLogin() },
            content = {
                Text(
                    text = "Entrar",
                    color = Color.White,
                    fontFamily = ralewayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        )
    }
}