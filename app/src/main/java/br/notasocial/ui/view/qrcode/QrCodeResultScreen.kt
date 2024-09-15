package br.notasocial.ui.view.qrcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.viewmodel.QrCodeResultViewModel
import br.notasocial.ui.viewmodel.ReceiptUiState


object QrCodeResultDestination : NavigationDestination {
    override val route = "qrcoderesult"
    override val title = "Qr Code Result"
    const val receiptIdArg = "receiptId"
    val routeWithArgs = "$route/{$receiptIdArg}"
}

@Composable
fun QrCodeResultScreen(
    onRequestPermission: () -> Unit,
    textResult: MutableState<String>,
    navigateToCatalog: () -> Unit,
    navigateToQrCodeResult: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: QrCodeResultViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState: ReceiptUiState = viewModel.receiptUiState
    when (uiState) {
        is ReceiptUiState.Loading -> LoadingScreen()
        is ReceiptUiState.Error -> QrCodeErrorScreen(
            onRequestPermission = onRequestPermission,
            textResult = textResult,
            receiptUrl = viewModel.receiptUrl,
            setReceiptUrlId = {
                viewModel.setReceiptUrlId(it)
            },
            navigateToQrCodeResult = navigateToQrCodeResult
        )
        is ReceiptUiState.Success -> QrCodeSuccessScreen(navigateToCatalog = navigateToCatalog)
    }

}

@Composable
fun LoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.hsl(0f,0f,.97f,1f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "",
            modifier = Modifier.size(192.dp)
        )
    }
}

@Composable
fun QrCodeSuccessScreen(
    navigateToCatalog: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, 0.97f, 1f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .width(260.dp)
                .padding(bottom = 20.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.receipt),
                contentDescription = "",
                tint = Color.hsl(123f, .66f, .33f, 1f),
                modifier = Modifier
                    .size(96.dp)
                    .padding(end = 15.dp)
            )
            Column(

            ) {
                Text(
                    text = "Nota cadastrada com sucesso!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.hsl(123f, .66f, .33f, 1f)
                )
            }
        }
        Button(
            onClick = { navigateToCatalog() },
            modifier = Modifier
                .width(260.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.hsl(123f, .66f, .33f, 1f)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = "Verificar Produtos",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun QrCodeErrorScreen(
    onRequestPermission: () -> Unit,
    receiptUrl: String,
    textResult: MutableState<String>,
    setReceiptUrlId: (String) -> Unit,
    navigateToQrCodeResult: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, 0.97f, 1f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                QrCodeIcon(onRequestPermission)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Erro na leitura",
            color = Color.hsl(0f, 1f, 0.50f, 1f), // Custom green color
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Faça a leitura do",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Row {
            Text(
                text = "QRCODE",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
            )
            Text(
                text = " novamente",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
    LaunchedEffect(key1 = textResult.value) {
        if (textResult.value.isNotBlank()) {
            setReceiptUrlId(textResult.value)
        }
    }
    LaunchedEffect(key1 = receiptUrl) {
        if (receiptUrl.isNotBlank()) {
            navigateToQrCodeResult(receiptUrl)
        }
    }
}





