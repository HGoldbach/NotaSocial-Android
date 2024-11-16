package br.notasocial.ui.view.consumidor.userprofile

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.NotaSocialBottomAppBar
import br.notasocial.ui.NotaSocialTopAppBar
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

object AccountDestination : NavigationDestination {
    override val route = "userprofile_account"
    override val title = "Conta"
}

@Composable
fun AccountScreen(
    navigateToBuscarProduto: () -> Unit,
    navigateToEstabelecimentos: () -> Unit,
    navigateToRanking: () -> Unit,
    navigateToFavoritos: () -> Unit,
    navigateToShoplist: () -> Unit,
    navigateToCadastrarNota: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToRegistrar: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToPerfilProprio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            NotaSocialTopAppBar(
                navigateToBuscarProduto = navigateToBuscarProduto,
                navigateToEstabelecimentos = navigateToEstabelecimentos,
                navigateToRanking = navigateToRanking,
                navigateToFavoritos = navigateToFavoritos,
                navigateToShoplist = navigateToShoplist,
                navigateToCadastrarNota = navigateToCadastrarNota,
                navigateToLogin = navigateToLogin,
                navigateToRegistrar = navigateToRegistrar,
                navigateToHome = navigateToHome
            )
        },
        bottomBar = {
            NotaSocialBottomAppBar(
                navigateToHome = navigateToHome,
                navigateToBuscarProduto = navigateToBuscarProduto,
                navigateToPerfilProprio = navigateToPerfilProprio
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(Color.hsl(0f, 0f, .97f, 1f))
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                HomeTopSection()
                AccountFormSection()
            }
        }
    }
}

@Composable
fun AccountFormSection(
    modifier: Modifier = Modifier
) {
    var mudarSenha by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        AccountInputField(
            text = "Nome",
            modifier = Modifier.padding(top = 30.dp)
        )
        AccountInputField(
            text = "Sobrenome",
            modifier = Modifier.padding(vertical = 12.dp)
        )
        AccountInputField(
            text = "Email"
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
                painter =  painterResource(id =
                    if(mudarSenha) {
                        R.drawable.angle_up_solid
                    } else {
                        R.drawable.angle_down_solid
                    }
                ),
                contentDescription = "",
                modifier = Modifier.size(14.dp).clickable {
                    mudarSenha = !mudarSenha
                }
            )
        }
        if(mudarSenha) {
            AccountInputField(
                text = "Senha",
                modifier = Modifier.padding(vertical = 12.dp)
            )
            AccountInputField(
                text = "Confirmar Senha",
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.End
        ) {
            TextButton(
                onClick = {},
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.textButtonColors(
                    containerColor = Color.hsl(123f, .63f, .33f, 1f),
                    contentColor = Color.White
                ),
                modifier = Modifier.width(120.dp).height(45.dp)
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

@Composable
fun AccountInputField(
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        OutlinedTextField(
            value = "",
            onValueChange = {},
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.hsl(0f, 0f, .85f, 1f),
                unfocusedBorderColor = Color.hsl(0f, 0f, .85f, 1f),
            ),
            modifier = Modifier.fillMaxWidth().height(45.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountScreenPreview() {
    NotasocialTheme {
        AccountScreen(
            {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AccountInputFieldPreview() {
    NotasocialTheme {
        AccountInputField(
            text = "Nome"
        )
    }
}