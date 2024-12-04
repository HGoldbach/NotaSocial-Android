package br.notasocial.ui.view.customer.contact

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.notasocial.R
import br.notasocial.ui.AppViewModelProvider
import br.notasocial.ui.navigation.NavigationDestination
import br.notasocial.ui.theme.ralewayFamily
import br.notasocial.ui.viewmodel.customer.contact.ContactViewModel

object ContactDestination : NavigationDestination {
    override val route = "contact"
    override val title = "Contact"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    viewModel: ContactViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var recipient by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    val emailStatus by viewModel.emailStatus.collectAsState()
    val options = listOf(
        "Sugestão",
        "Dúvida",
        "Problema Técnico",
        "Reclamação",
        "Elogio",
        "Outro"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.hsl(0f, 0f, .97f, 1f)),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.hsl(0f, 0f, .97f, 1f))
                .padding(20.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.nota_social_typho),
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Text(
                text = "Contato",
                fontFamily = ralewayFamily,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = recipient,
                onValueChange = { recipient = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email", color = Color.Black) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.hsl(123f, .63f, .33f, 1f)
                ),
                textStyle = TextStyle(color = Color.Black)
            )

            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = subject,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    label = { Text("Assunto", color = Color.Black) },
                    readOnly = true,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            expanded = !expanded
                        }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Abrir menu"
                            )
                        }
                    },
                    textStyle = TextStyle(color = Color.Black),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.hsl(123f, .63f, .33f, 1f)
                    )
                )
                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                subject = option
                                expanded = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = Color.Black,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                    }
                }
            }
            var buttonClicked by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = body,
                onValueChange = { body = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(vertical = 5.dp),
                label = { Text("Corpo do E-mail", color = Color.Black) },
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.hsl(123f, .63f, .33f, 1f)
                ),
                textStyle = TextStyle(color = Color.Black)
            )
            Button(
                onClick = {
                    buttonClicked = !buttonClicked
                    if(recipient.isNotEmpty() && subject.isNotEmpty() && body.isNotEmpty()) viewModel.sendEmail(recipient, subject, body)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.hsl(123f, .63f, .33f, 1f),
                    contentColor = Color.White
                )
            ) {
                Text(text = "Enviar")
            }

            if (buttonClicked && (recipient.isEmpty() || subject.isEmpty() || body.isEmpty())) {
                Text(
                    text = "Preencha todos os campos",
                    modifier = Modifier.padding(top = 16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            } else if (emailStatus.isEmpty()) {
                Text(
                    text = emailStatus,
                    modifier = Modifier.padding(top = 16.dp),
                    color = if (emailStatus.contains("sucesso", ignoreCase = true)) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    }
                )
            }
        }
    }
}

