package br.notasocial

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import br.notasocial.ui.NotaSocialApp
import br.notasocial.ui.theme.NotasocialTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions


class MainActivity : ComponentActivity() {

    private val textResult: MutableState<String> = mutableStateOf("")

    private val barCodeLauncher: ActivityResultLauncher<ScanOptions> = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        if (result.contents != null) {
            textResult.value = result.contents
            Log.d("MAINACTIVITY", textResult.value)
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private val requestPermissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showCamera()
        }
    }

    private fun showCamera() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Scan a QR code")
            setCameraId(0)
            setBeepEnabled(false)
            setOrientationLocked(false)
        }
        barCodeLauncher.launch(options)
    }

    private fun checkCameraPermission(context: Context) {
        when {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                showCamera()
            }
            !shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            }
            else -> {
                Toast.makeText(this, "Camera Required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotasocialTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NotaSocialApp(
                        checkCameraPermission = { checkCameraPermission(this) },
                        textResult = textResult
                    )
                }
            }
        }
    }
}




