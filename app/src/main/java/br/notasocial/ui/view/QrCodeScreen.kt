import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.viewmodel.QrCodeViewModel

object QrCodeDestination : NavigationDestination {
    override val route = "qrcode"
    override val title = "Qr Code Screen"
}

@Composable
fun QrCodeScreen(
    onRequestPermission: () -> Unit,
    navigateToQrCodeResult: (String) -> Unit,
    textResult: MutableState<String>,
    modifier: Modifier = Modifier,
    viewModel: QrCodeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.hsl(0f, 0f, 0.97f, 1f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nota Social",
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = Color.hsl(123f, .66f, .33f, 1f),
            modifier = Modifier.padding(top = 32.dp)
        )
        Column(
            modifier = Modifier.fillMaxHeight(),
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
                text = "Faça a leitura do",
                color = Color.hsl(123f, 0.66f, 0.33f, 1f), // Custom green color
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Row {
                Text(
                    text = "QRCODE",
                    color = Color.hsl(123f, 0.66f, 0.33f, 1f), // Custom green color
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = " da sua nota",
                    color = Color.hsl(123f, 0.66f, 0.33f, 1f), // Custom green color
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            LaunchedEffect(key1 = textResult.value) {
                if (textResult.value.isNotBlank()) {
                    viewModel.setReceiptUrlId(textResult.value)
                }
            }
            LaunchedEffect(key1 = viewModel.receiptUrl) {
                if (viewModel.receiptUrl.isNotBlank()) {
                    navigateToQrCodeResult(viewModel.receiptUrl)
                }
            }
        }
    }

}


@Composable
fun QrCodeIcon(
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(200.dp)
            .background(Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.qrcode_solid),
            contentDescription = null,
            modifier = Modifier.size(96.dp),
            colorFilter = ColorFilter.tint(Color.hsl(123f, 0.66f, 0.33f, 1f))
        )
        IconButton(
            onClick = { onRequestPermission() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .zIndex(1f)
                .size(65.dp)
                .background(Color.White, shape = CircleShape)
                .border(8.dp, Color.hsl(0f, 0f, 0.97f, 1f), shape = RoundedCornerShape(80.dp))
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.camera_solid),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF3A9100) // Custom green color
            )
        }
    }
}
