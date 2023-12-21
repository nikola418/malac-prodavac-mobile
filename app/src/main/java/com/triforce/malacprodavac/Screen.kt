package com.triforce.malacprodavac

sealed class Screen(val route: String) {
    data object LoginScreen : Screen("login_screen")
    data object RegistrationScreen : Screen("register_screen")
    data object HomeScreen : Screen("home_screen")
    data object StoreScreen : Screen("store_screen")
    data object StoreCategoryScreen : Screen("store_category_screen")
    data object ProductScreen : Screen("product_screen")
    data object CartScreen : Screen("cart_screen")
    data object CartDetailsScreen : Screen("cart_deatils_screen")
    data object DetailsOrderScreen : Screen("details_order_screen")
    data object ShopHomeScreen : Screen("shop_home_screen")
    data object HighlightSection : Screen("highlight_section")
    data object AddProduct : Screen("add_product")
    data object EditProduct : Screen("edit_product")
    data object AddProductImage : Screen("add_product_image")
    data object OrderScreen : Screen("order_screen")
    data object TransactionScreen : Screen("transaction_screen")
    data object FavoriteProductsScreen : Screen("favorite_products")
    data object MapScreen : Screen("map_screen")
    data object SchedulingScreen : Screen("scheduling_screen")
    data object MyProductsScreen : Screen("my_products_screen")
    object FavoriteShopScreen : Screen("favorite_shops")
    data object NotificationScreen : Screen("notification_screen") {
        const val DEEPLINK_URI = "http://softeng.pmf.kg.ac.rs:10010/notifications"
    }

    data object PrivateProfile : Screen("private_profile_screen")
    data object PublicProfile : Screen("public_profile_screen")
    data object ShopPrivateScreen : Screen("shop_private_screen")
    data object CourierPrivateScreen : Screen("courier_private_screen")
    data object CustomerPrivateScreen : Screen("customer_private_screen")
    data object AdvertisingProductScreen : Screen("advertising_product_screen")

    data object MyDeliveries : Screen("my_deliveries")
    data object MyPurchases : Screen("my_purchases")
    data object MySales : Screen("my_sales")
    data object TransactionDetails: Screen("transaction_details")
    data object TransactionHistory : Screen("transaction_history")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

}