package br.notasocial.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.notasocial.R
import br.notasocial.ui.theme.NotasocialTheme
import br.notasocial.ui.theme.ralewayFamily

@Composable
fun InputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    label: String,
    isError: Boolean = false,
    icon: ImageVector? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize = 12.sp,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 12.sp,
                fontFamily = ralewayFamily,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        },
        leadingIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isError) Color.Red else Color.hsl(123f, .63f, .33f)
                )
            }
        },
        trailingIcon = {
            if (placeholder == "SENHA" || placeholder == "CONFIRMAR SENHA") {
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
        isError = isError,
        keyboardOptions = keyboardOptions,
        singleLine = true,
        visualTransformation = if (passwordVisible && (placeholder == "SENHA" || placeholder == "CONFIRMAR SENHA")) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .background(Color.Transparent)
            .semantics {
                // Fornecer informações de acessibilidade
            },
        shape = RoundedCornerShape(15.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.hsl(123f, .63f, .33f, 1f),
            unfocusedTextColor = Color.hsl(123f, .63f, .33f, 1f),
            cursorColor = Color.hsl(123f, .63f, .33f, 1f),
            unfocusedContainerColor = Color.hsl(0f, 0f, 1f, .46f),
            focusedContainerColor = Color.hsl(0f, 0f, 1f, .66f),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            errorBorderColor = Color.Red
        )
    )
}

@Preview
@Composable
fun InputFieldPreview() {
    var text by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("")}
    var confirmPassword by remember { mutableStateOf("")}
    var isError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isConfirmPasswordError by remember { mutableStateOf(false) }

    NotasocialTheme {
        InputField(
            value = text,
            onValueChange = { text = it },
            placeholder = "SENHA",
            label = "SENHA",
            icon = Icons.Default.Lock,
            modifier = Modifier
        )
    }

}