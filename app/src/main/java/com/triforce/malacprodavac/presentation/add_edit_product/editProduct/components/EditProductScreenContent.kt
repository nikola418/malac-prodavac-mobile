package com.triforce.malacprodavac.presentation.add_edit_product.editProduct.components

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.domain.model.Category
import com.triforce.malacprodavac.domain.util.enum.Currency
import com.triforce.malacprodavac.domain.util.enum.UnitOfMeasurement
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditDropDownList
import com.triforce.malacprodavac.presentation.add_edit_product.components.AddEditTextField
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductEvent
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductViewModel
import com.triforce.malacprodavac.presentation.product.components.ProductHeroImage
import com.triforce.malacprodavac.ui.theme.MP_White

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun EditProductScreenContent(
    navController: NavController,
    viewModel: EditProductViewModel,
    padding: PaddingValues
) {
    val context = LocalContext.current

    val state = viewModel.state
    val product = state.product

    val scrollState = rememberScrollState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = {}
    )

    if (state.isUpdateSuccessful && product != null) {
        Toast
            .makeText(
                context,
                "Uspešno ste izmenili proizvod!",
                Toast.LENGTH_LONG,
            )
            .show()
        LaunchedEffect(Unit) {
            navController.navigate(Screen.ProductScreen.route + "?productId=${product.id}") {
                popUpTo(Screen.AddProduct.route) {
                    inclusive = true
                }
            }
        }
    }


    val permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES else Manifest.permission.READ_EXTERNAL_STORAGE
    val permissionState = rememberPermissionState(permission = permission)

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null)
            viewModel.onEvent(EditProductEvent.ChangeProductImage(uri))

    }

    val paddingBetween = 12.dp

    Box(
        modifier = Modifier
            .padding(padding)
            .pullRefresh(pullRefreshState)
            .verticalScroll(state = scrollState)
            .background(MP_White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(830.dp)
        ) {
            ProductHeroImage(
                modifier = Modifier.clickable {
                    if (!permissionState.status.isGranted) {
                        permissionState.launchPermissionRequest()
                    } else {
                        launcher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                },
                imageUrl = state.thumbUrl,
                imageKey = state.thumbKey
            )
            Spacer(modifier = Modifier.padding(paddingBetween))

            AddEditTextField(
                label = "Naziv proizvoda",
                isError = state.titleError != null,
                text = state.product?.title ?: "",
                onTextValueChange = {
                    viewModel.onEvent(EditProductEvent.TitleChanged(it))
                },
                placeholder = "Naziv"
            )
            Text(state.titleError ?: "", color = Color.Red)

            AddEditTextField(
                label = "Opis proizvoda",
                text = state.product?.desc ?: "",
                onTextValueChange = {
                    viewModel.onEvent(EditProductEvent.DescChanged(it))
                },
                placeholder = "Opis"
            )
            Spacer(modifier = Modifier.padding(paddingBetween))

            AddEditDropDownList(
                entries = state.categories,
                selectedEntry = state.categories.find { it.id == state.product?.categoryId }
                    ?.toString(),
                handleSelect = { category ->
                    viewModel.onEvent(
                        EditProductEvent.CategoryIdChanged(
                            (category as Category).id
                        )
                    )
                },
                label = "Kategorija",
                fill = true
            )
            Spacer(modifier = Modifier.padding(paddingBetween))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                AddEditDropDownList(
                    entries = enumValues<UnitOfMeasurement>().toList(),
                    selectedEntry = state.product?.unitOfMeasurement?.toString(),
                    handleSelect = { unit ->
                        viewModel.onEvent(
                            EditProductEvent.UnitOfMeasurementChanged(
                                unit as UnitOfMeasurement
                            )
                        )
                    },
                    label = "Mera",
                    fill = false
                )

                AddEditDropDownList(
                    entries = enumValues<Currency>().toList(),
                    selectedEntry = state.product?.currency?.toString(),
                    handleSelect = { currency ->
                        viewModel.onEvent(
                            EditProductEvent.CurrencyChanged(
                                currency as Currency
                            )
                        )
                    },
                    label = "Valuta",
                    fill = false
                )
            }
            Spacer(modifier = Modifier.padding(paddingBetween))

            AddEditTextField(
                label = "Cena",
                isError = state.priceError != null,
                text = product?.price.toString(),
                onTextValueChange = {
                    viewModel.onEvent(EditProductEvent.PriceChanged(it.toDouble()))
                },
                placeholder = "0.00"
            )
            Text(state.priceError ?: "", color = Color.Red)
            Spacer(modifier = Modifier.padding(paddingBetween))
        }
    }
}