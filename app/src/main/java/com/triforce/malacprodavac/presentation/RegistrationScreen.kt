package com.triforce.malacprodavac.presentation
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.triforce.malacprodavac.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavController) {
    val viewModel = viewModel<MainViewModel>()
    val state = viewModel.state
    val context = LocalContext.current
    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when(event) {
                is MainViewModel.ValidationEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Uspešna registracija",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.firstName,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.FirstNameChanged(it))
            },
            isError = state.firstNameError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Ime")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        if(state.firstNameError != null) {
            Text(
                text = state.firstNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.lastName,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.LastNameChanged(it))
            },
            isError = state.lastNameError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Prezime")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
        if(state.lastNameError != null) {
            Text(
                text = state.lastNameError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.EmailChanged(it))
            },
            isError = state.emailError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        if(state.emailError != null) {
            Text(
                text = state.emailError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.PasswordChanged(it))
            },
            isError = state.passwordError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Lozinka")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if(state.passwordError != null) {
            Text(
                text = state.passwordError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.repeatedPassword,
            onValueChange = {
                viewModel.onEvent(RegistrationFormEvent.RepeatedPasswordChanged(it))
            },
            isError = state.repeatedPasswordError != null,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Ponovljena lozinka")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        if(state.repeatedPasswordError != null) {
            Text(
                text = state.repeatedPasswordError,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.End)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        DropDownList()

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            Checkbox(
                checked = state.acceptedTerms,
                onCheckedChange = {
                    viewModel.onEvent(RegistrationFormEvent.AcceptTermsChanged(it))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Prihvati uslove")
        }
        if(state.termsError != null) {
            Text(
                text = state.termsError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(onClick = {
            viewModel.onEvent(RegistrationFormEvent.Submit)
            if(!viewModel.hasError)
                navController.navigate(Screen.LoginScreen.route)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Registruj se")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownList() {
    val rolesList = listOf(
        "KUPAC",
        "DOSTAVLJAČ",
        "PRODAVAC"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf(rolesList[0]) }

    // menu box
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(), // menuAnchor modifier must be passed to the text field for correctness.
            readOnly = true,
            value = selectedRole,
            onValueChange = {},
            label = { Text("Roles") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        // menu
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },) {
            rolesList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption)},
                    onClick = {
                        selectedRole = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}