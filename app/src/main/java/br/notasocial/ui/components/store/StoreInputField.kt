package br.notasocial.ui.components.store

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily
import kotlin.math.absoluteValue

@Composable
fun InputField(
    text: String,
    icon: Int? = null,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    placeholder: String? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ralewayFamily,
            modifier = Modifier.padding(bottom = 5.dp)
        )
        Row(
            modifier = Modifier
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                isError = isError,
                keyboardOptions = keyboardOptions,
                singleLine = true,
                visualTransformation = if (text == "Senha" || text == "Confirmar Senha") {
                    if (passwordVisible) visualTransformation else PasswordVisualTransformation()
                } else {
                    visualTransformation
                },
                modifier = if (icon == null) {
                    Modifier
                        .height(50.dp)
                        .fillMaxWidth()
                } else {
                    Modifier
                        .height(45.dp)
                        .weight(1f)
                        .padding(end = 10.dp)
                },
                enabled = enabled,
                placeholder = {
                    if (placeholder != null) {
                        Text(
                            text = placeholder,
                            fontSize = 12.sp,
                            fontFamily = ralewayFamily,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                },
                trailingIcon = {
                    if (text == "Senha" || text == "Confirmar Senha") {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible)
                                    painterResource(id = R.drawable.visibilityoff_solid)
                                else
                                    painterResource(id = R.drawable.visibility_solid),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }
                },
                textStyle = TextStyle.Default.copy(fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    cursorColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledPlaceholderColor = Color.White,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedBorderColor = Color.hsl(0f, 0f, .85f, 1f),
                    focusedBorderColor = Color.hsl(0f, 0f, .85f, 1f),
                    errorBorderColor = Color.Red,
                    errorSupportingTextColor = Color.White,
                    errorContainerColor = Color.hsl(0f, 1f, .44f, .46f)
                ),
            )
            if (icon != null) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    tint = Color.hsl(123f, 0.66f, 0.33f, 1f),
                    modifier = Modifier.size(45.dp)
                )
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun InputFieldPasswordPreview() {
    var text by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    NotasocialTheme {
        InputField(
            text = "Titulo",
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
        )
    }

}

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}


