package com.triforce.malacprodavac

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.triforce.malacprodavac.presentation.FavProducts.FavoriteScreen
import com.triforce.malacprodavac.presentation.FavShops.FavoriteShopScreen
import com.triforce.malacprodavac.presentation.add_edit_product.addProduct.AddProductScreen
import com.triforce.malacprodavac.presentation.add_edit_product.addProductImage.AddProductImageScreen
import com.triforce.malacprodavac.presentation.add_edit_product.advertisingProduct.AdvertisingProductScreen
import com.triforce.malacprodavac.presentation.add_edit_product.editProduct.EditProductScreen
import com.triforce.malacprodavac.presentation.cart.CartDetails.CartDetailsScreen
import com.triforce.malacprodavac.presentation.cart.CartScreen
import com.triforce.malacprodavac.presentation.cart.DetailsOrder.DetailsOrderScreen
import com.triforce.malacprodavac.presentation.cart.scheduling.ScheduleScreen
import com.triforce.malacprodavac.presentation.highlightSection.HighlightSection
import com.triforce.malacprodavac.presentation.home.HomeScreen
import com.triforce.malacprodavac.presentation.home.shopHome.ShopHomeScreen
import com.triforce.malacprodavac.presentation.login.LoginScreen
import com.triforce.malacprodavac.presentation.maps.MapScreen
import com.triforce.malacprodavac.presentation.myProducts.MyProductsScreen
import com.triforce.malacprodavac.presentation.notifications.NotificationsScreen
import com.triforce.malacprodavac.presentation.orders.OrderScreen
import com.triforce.malacprodavac.presentation.product.ProductScreen
import com.triforce.malacprodavac.presentation.profile.profilePrivate.ProfilePrivateScreen
import com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens.CourierPrivateScreen
import com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens.CustomerPrivateScreen
import com.triforce.malacprodavac.presentation.profile.profilePrivate.userScreens.ShopPrivateScreen
import com.triforce.malacprodavac.presentation.profile.profilePublic.ProfilePublicScreen
import com.triforce.malacprodavac.presentation.registration.RegistrationScreen
import com.triforce.malacprodavac.presentation.store.StoreScreen
import com.triforce.malacprodavac.presentation.store.category.StoreCategoryScreen
import com.triforce.malacprodavac.presentation.transactions.TransactionScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val startDestination = Screen.LoginScreen.route
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.RegistrationScreen.route + "",
            arguments = listOf(
//                navArgument("name") {
//                    type = NavType.StringType
//                    defaultValue = "Uros"
//                    nullable = true
//                }
            )
        ) {
            RegistrationScreen(navController = navController)
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.StoreScreen.route) {
            StoreScreen(navController = navController)
        }

        composable(
            route = Screen.StoreCategoryScreen.route + "?categoryId={categoryId}&title={title}",
            arguments = listOf(

                navArgument(
                    name = "categoryId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "title"
                ) {
                    type = NavType.StringType
                    defaultValue = ""
                }

            )) {
            StoreCategoryScreen(navController = navController)
        }

        composable(route = Screen.CartScreen.route + "?productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            CartScreen(navController = navController)
        }

        composable(route = Screen.CartDetailsScreen.route) {
            CartDetailsScreen(navController = navController)
        }

        composable(route = Screen.DetailsOrderScreen.route) {
            DetailsOrderScreen(navController = navController)
        }

        composable(
            route = Screen.ProductScreen.route + "?productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            ProductScreen(navController = navController)
        }

        composable(
            route = Screen.PublicProfile.route + "?id={id}&role={role}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument(
                    name = "role"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            ProfilePublicScreen(navController = navController)
        }

        composable(route = Screen.ShopHomeScreen.route) {
            ShopHomeScreen(navController = navController)
        }

        composable(route = Screen.HighlightSection.route + "?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            HighlightSection(navController = navController)
        }

        composable(route = Screen.MyProductsScreen.route) {
            MyProductsScreen(navController = navController)
        }

        composable(route = Screen.AddProduct.route) {
            AddProductScreen(navController = navController)
        }

        composable(
            route = Screen.EditProduct.route + "?productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            EditProductScreen(navController = navController)
        }

        composable(
            route = Screen.AddProductImage.route + "?productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddProductImageScreen(navController = navController)
        }

        composable(route = Screen.OrderScreen.route) {
            OrderScreen(navController = navController)
        }

        composable(route = Screen.MapScreen.route) {
            MapScreen(navController = navController)
        }

        composable(route = Screen.TransactionScreen.route) {
            TransactionScreen(navController = navController)
        }

        composable(route = Screen.FavoriteProductsScreen.route) {
            FavoriteScreen(navController = navController)
        }

        composable(route = Screen.SchedulingScreen.route) {
            ScheduleScreen(navController = navController)
        }

        composable(route = Screen.FavoriteShopScreen.route) {
            FavoriteShopScreen(navController = navController)
        }

        composable(
            route = Screen.NotificationScreen.route,
            deepLinks = listOf(navDeepLink { uriPattern = Screen.NotificationScreen.DEEPLINK_URI })
        ) {
            NotificationsScreen(navController = navController)
        }

        composable(route = Screen.PrivateProfile.route) {
            ProfilePrivateScreen(navController = navController)
        }

        composable(route = Screen.ShopPrivateScreen.route) {
            ShopPrivateScreen(navController = navController)
        }

        composable(route = Screen.CourierPrivateScreen.route) {
            CourierPrivateScreen(navController = navController)
        }

        composable(route = Screen.CustomerPrivateScreen.route) {
            CustomerPrivateScreen(navController = navController)
        }

        // my transactions

        composable(route = Screen.CustomerPrivateScreen.route) {
            CustomerPrivateScreen(navController = navController)
        }

        composable(route = Screen.CustomerPrivateScreen.route) {
            CustomerPrivateScreen(navController = navController)
        }

        composable(route = Screen.CustomerPrivateScreen.route) {
            CustomerPrivateScreen(navController = navController)
        }

        composable(route = Screen.CustomerPrivateScreen.route) {
            CustomerPrivateScreen(navController = navController)
        }

        composable(route = Screen.AdvertisingProductScreen.route + "?productId={productId}",
            arguments = listOf(
                navArgument(
                    name = "productId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )) {
            AdvertisingProductScreen(navController = navController)
        }
    }
}