package br.notasocial.ui.view.login


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

object LoginScreenDestination : NavigationDestination {
    override val route = "loginscreen"
    override val title = "Login Screen"
}

@Composable
fun LoginScreen(
    navigateToRegistrar: () -> Unit,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
            .background(color = Color.hsl(128f,.52f,.47f,1f))
            .padding(35.dp)
    ) {
        TopSection(
            navigateUp = navigateUp
        )
        FormSection(modifier = Modifier.weight(1f))
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
    modifier: Modifier = Modifier
) {
    var email by remember {
        mutableStateOf("")
    }
    var senha by remember {
        mutableStateOf("")
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth().padding(top = 50.dp)
    ) {
        InputField(
            value = email,
            onValueChange = { email = it },
            placeholder = "EMAIL",
            label = "EMAIL",
            icon = Icons.Default.Email
        )
        Spacer(modifier = Modifier.height(30.dp))
        InputField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = "SENHA",
            label = "SENHA",
            icon = Icons.Default.Lock
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {},
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

/*
@Composable
fun InputField(
    placeholder: String
) {

    var value by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 12.sp,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        },
        modifier = Modifier.fillMaxWidth().height(55.dp).background(Color.Transparent),
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.hsl(123f,.63f,.33f,1f),
            unfocusedTextColor = Color.hsl(123f,.63f,.33f,1f),
            cursorColor = Color.hsl(123f,.63f,.33f,1f),
            unfocusedContainerColor = Color.hsl(0f,0f,1f, .46f),
            focusedContainerColor = Color.hsl(0f,0f,1f, .66f),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent
        )
    )
}

*/

