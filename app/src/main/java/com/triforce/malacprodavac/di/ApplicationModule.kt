package com.triforce.malacprodavac.di

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.triforce.malacprodavac.MainActivity
import com.triforce.malacprodavac.R
import com.triforce.malacprodavac.Screen
import com.triforce.malacprodavac.data.local.MalacProdavacDatabase
import com.triforce.malacprodavac.data.local.RoomConverters
import com.triforce.malacprodavac.data.remote.Api
import com.triforce.malacprodavac.data.remote.auth.AuthApi
import com.triforce.malacprodavac.data.remote.auth.interceptors.AuthInterceptorImpl
import com.triforce.malacprodavac.data.remote.categories.CategoriesApi
import com.triforce.malacprodavac.data.remote.couriers.CouriersApi
import com.triforce.malacprodavac.data.remote.customers.CustomersApi
import com.triforce.malacprodavac.data.remote.notifications.NotificationsApi
import com.triforce.malacprodavac.data.remote.orders.OrderApi
import com.triforce.malacprodavac.data.remote.products.ProductsApi
import com.triforce.malacprodavac.data.remote.products.productMedias.ProductMediasApi
import com.triforce.malacprodavac.data.remote.products.reviews.ReviewsApi
import com.triforce.malacprodavac.data.remote.products.reviews.replies.ReviewRepliesApi
import com.triforce.malacprodavac.data.remote.shops.ShopsApi
import com.triforce.malacprodavac.data.remote.users.UsersApi
import com.triforce.malacprodavac.data.remote.users.userMedias.UserMediasApi
import com.triforce.malacprodavac.data.repository.products.productMedias.ProductMediasRepositoryImpl
import com.triforce.malacprodavac.data.services.AppSharedPreferences
import com.triforce.malacprodavac.data.services.SessionManager
import com.triforce.malacprodavac.domain.repository.AuthRepository
import com.triforce.malacprodavac.domain.repository.CategoriesRepository
import com.triforce.malacprodavac.domain.repository.CourierRepository
import com.triforce.malacprodavac.domain.repository.CustomerRepository
import com.triforce.malacprodavac.domain.repository.OrderRepository
import com.triforce.malacprodavac.domain.repository.ShopRepository
import com.triforce.malacprodavac.domain.repository.notifications.NotificationsRepository
import com.triforce.malacprodavac.domain.repository.products.ProductRepository
import com.triforce.malacprodavac.domain.repository.products.produtMedias.ProductMediasRepository
import com.triforce.malacprodavac.domain.repository.products.reviews.ReviewsRepository
import com.triforce.malacprodavac.domain.repository.products.reviews.replies.ReviewRepliesRepository
import com.triforce.malacprodavac.domain.repository.users.userMedias.UserMediasRepository
import com.triforce.malacprodavac.domain.use_case.GetToken
import com.triforce.malacprodavac.domain.use_case.auth.IsAuthenticated
import com.triforce.malacprodavac.domain.use_case.category.CategoryUseCase
import com.triforce.malacprodavac.domain.use_case.category.GetCategories
import com.triforce.malacprodavac.domain.use_case.category.GetCategory
import com.triforce.malacprodavac.domain.use_case.couriers.CourierUseCase
import com.triforce.malacprodavac.domain.use_case.couriers.CreateCourier
import com.triforce.malacprodavac.domain.use_case.couriers.GetCourier
import com.triforce.malacprodavac.domain.use_case.couriers.GetCouriers
import com.triforce.malacprodavac.domain.use_case.couriers.UpdateCourierUseCase
import com.triforce.malacprodavac.domain.use_case.customers.UpdateCustomerUseCase
import com.triforce.malacprodavac.domain.use_case.favoriteProduct.AddFavProduct
import com.triforce.malacprodavac.domain.use_case.favoriteProduct.DeleteFavProduct
import com.triforce.malacprodavac.domain.use_case.favoriteProduct.FavoriteProduct
import com.triforce.malacprodavac.domain.use_case.favoriteProduct.GetFavProducts
import com.triforce.malacprodavac.domain.use_case.favoriteShop.AddFavShop
import com.triforce.malacprodavac.domain.use_case.favoriteShop.DeleteFavShop
import com.triforce.malacprodavac.domain.use_case.favoriteShop.FavShopUseCase
import com.triforce.malacprodavac.domain.use_case.favoriteShop.GetFavShop
import com.triforce.malacprodavac.domain.use_case.login.Login
import com.triforce.malacprodavac.domain.use_case.login.LoginUser
import com.triforce.malacprodavac.domain.use_case.login.Me
import com.triforce.malacprodavac.domain.use_case.notifications.GetNotifications
import com.triforce.malacprodavac.domain.use_case.notifications.NotificationsUseCase
import com.triforce.malacprodavac.domain.use_case.order.AddOrder
import com.triforce.malacprodavac.domain.use_case.order.DeleteOrder
import com.triforce.malacprodavac.domain.use_case.order.GetAllOrders
import com.triforce.malacprodavac.domain.use_case.order.GetOrderForId
import com.triforce.malacprodavac.domain.use_case.order.Order
import com.triforce.malacprodavac.domain.use_case.order.UpdateOrder
import com.triforce.malacprodavac.domain.use_case.product.AddProduct
import com.triforce.malacprodavac.domain.use_case.product.GetAllProducts
import com.triforce.malacprodavac.domain.use_case.product.GetProductForId
import com.triforce.malacprodavac.domain.use_case.product.ProductUseCase
import com.triforce.malacprodavac.domain.use_case.product.SetProductImage
import com.triforce.malacprodavac.domain.use_case.product.UpdateProduct
import com.triforce.malacprodavac.domain.use_case.product.replies.CreateReview
import com.triforce.malacprodavac.domain.use_case.product.replies.GetReview
import com.triforce.malacprodavac.domain.use_case.product.replies.GetReviews
import com.triforce.malacprodavac.domain.use_case.product.replies.ReviewUseCase
import com.triforce.malacprodavac.domain.use_case.product.replies.UpdateReview
import com.triforce.malacprodavac.domain.use_case.product.replies.replies.CreateReviewReply
import com.triforce.malacprodavac.domain.use_case.product.replies.replies.GetReviewReplies
import com.triforce.malacprodavac.domain.use_case.product.replies.replies.GetReviewReply
import com.triforce.malacprodavac.domain.use_case.product.replies.replies.ReviewReplyUseCase
import com.triforce.malacprodavac.domain.use_case.profile.Logout
import com.triforce.malacprodavac.domain.use_case.profile.Profile
import com.triforce.malacprodavac.domain.use_case.profile.SetProfilePicture
import com.triforce.malacprodavac.domain.use_case.registration.RegisterCourier
import com.triforce.malacprodavac.domain.use_case.registration.RegisterCustomer
import com.triforce.malacprodavac.domain.use_case.registration.RegisterShop
import com.triforce.malacprodavac.domain.use_case.registration.Registration
import com.triforce.malacprodavac.domain.use_case.schedulePickup.AddSchedulePickup
import com.triforce.malacprodavac.domain.use_case.schedulePickup.GetAllScheduledPickups
import com.triforce.malacprodavac.domain.use_case.schedulePickup.GetSchedulePickupForId
import com.triforce.malacprodavac.domain.use_case.schedulePickup.SchedulePickupUseCase
import com.triforce.malacprodavac.domain.use_case.shops.UpdateShopUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder().add(
            KotlinJsonAdapterFactory()
        ).build()


    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit =
        Retrofit.Builder().client(client).baseUrl(Api.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()


    /*  USER API DEPENDENCY CHAIN */

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi =
        retrofit.create()

    @Provides
    @Singleton
    fun provideNotificationsApi(retrofit: Retrofit): NotificationsApi =
        retrofit.create()


    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi =
        retrofit.create()

    @Provides
    @Singleton
    fun provideUserMediasApi(retrofit: Retrofit): UserMediasApi =
        retrofit.create()


    @Provides
    @Singleton
    fun provideCustomersApi(retrofit: Retrofit): CustomersApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCategoriesApi(retrofit: Retrofit): CategoriesApi = retrofit.create()

    @Provides
    @Singleton
    fun provideProductsApi(retrofit: Retrofit): ProductsApi = retrofit.create()

    @Provides
    @Singleton
    fun provideCouriersApi(retrofit: Retrofit): CouriersApi = retrofit.create()


    @Provides
    @Singleton
    fun provideShopsApi(retrofit: Retrofit): ShopsApi = retrofit.create()

    @Provides
    @Singleton
    fun provideOrderApi(retrofit: Retrofit): OrderApi = retrofit.create()

    @Provides
    @Singleton
    fun provideProductMediasApi(retrofit: Retrofit): ProductMediasApi = retrofit.create()

    @Provides
    @Singleton
    fun provideReviewsApi(retrofit: Retrofit): ReviewsApi = retrofit.create()

    @Provides
    @Singleton
    fun provideReviewRepliesApi(retrofit: Retrofit): ReviewRepliesApi = retrofit.create()


    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptorImpl: AuthInterceptorImpl): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(0, TimeUnit.SECONDS)
            .addInterceptor(authInterceptorImpl)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Singleton
    @Provides
    fun provideAuthInterceptorImpl(
        sessionManager: SessionManager
    ): AuthInterceptorImpl = AuthInterceptorImpl(sessionManager)


    /* SESSION MANAGER DEPENDENCY CHAIN */
    @Singleton
    @Provides
    fun provideSessionManager(
        appSharedPreferences: AppSharedPreferences,
    ): SessionManager = SessionManager(appSharedPreferences)

    @Singleton
    @Provides
    fun provideAppSharedPreferences(
        sharedPreferences: SharedPreferences
    ): AppSharedPreferences = AppSharedPreferences(sharedPreferences)

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences =
        context.getSharedPreferences(AppSharedPreferences.SHARED_PREFS, Context.MODE_PRIVATE)

    /* DATABASE */

    @Provides
    @Singleton
    fun provideRoomConverters(moshi: Moshi): RoomConverters =
        RoomConverters(moshi)

    @Provides
    @Singleton
    fun provideRegisterCustomer(repository: CustomerRepository) =
        RegisterCustomer(repository)

    @Provides
    @Singleton
    fun provideRegisterCourier(repository: CourierRepository) =
        RegisterCourier(repository)

    @Provides
    @Singleton
    fun provideRegisterShop(repository: ShopRepository) =
        RegisterShop(repository)

    @Provides
    @Singleton
    fun provideRegistrationUseCase(
        registerCustomer: RegisterCustomer,
        registerCourier: RegisterCourier,
        registerShop: RegisterShop
    ) =
        Registration(registerCustomer, registerCourier, registerShop)


    @Provides
    @Singleton
    fun provideGetCategories(
        repository: CategoriesRepository
    ) = GetCategories(repository)

    @Provides
    @Singleton
    fun provideGetCategory(
        repository: CategoriesRepository
    ) = GetCategory(repository)


    @Provides
    @Singleton
    fun provideCategoryUseCase(
        getCategories: GetCategories,
        getCategory: GetCategory
    ) = CategoryUseCase(
        getCategories,
        getCategory
    )

    @Provides
    @Singleton
    fun provideGetNotificationsUseCase(repository: NotificationsRepository) =
        GetNotifications(repository)

    @Provides
    @Singleton
    fun provideNotificationUseCase(getNotifications: GetNotifications) =
        NotificationsUseCase(getNotifications)

    @Provides
    @Singleton
    fun provideLoginUser(repository: AuthRepository) =
        LoginUser(repository)

    @Provides
    @Singleton
    fun provideMe(repository: AuthRepository) =
        Me(repository)

    @Provides
    @Singleton
    fun provideLogoutUseCase(repository: AuthRepository) =
        Logout(repository)

    @Provides
    @Singleton
    fun provideLoginUseCase(loginUser: LoginUser, me: Me) =
        Login(loginUser, me)

    @Provides
    @Singleton
    fun provideGetToke(sessionManager: SessionManager) =
        GetToken(sessionManager)

    @Provides
    @Singleton
    fun provideSetProfilePicture(repository: UserMediasRepository) =
        SetProfilePicture(repository)

    @Provides
    @Singleton
    fun provideIsAuthenticatedUseCase(sessionManager: SessionManager) =
        IsAuthenticated(sessionManager)


    @Provides
    @Singleton
    fun provideUpdateCustomerUseCase(repository: CustomerRepository) =
        UpdateCustomerUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateCourierUseCase(repository: CourierRepository) =
        UpdateCourierUseCase(repository)

    @Provides
    @Singleton
    fun provideUpdateShopUseCase(repository: ShopRepository) =
        UpdateShopUseCase(repository)

    @Provides
    @Singleton
    fun provideProfileUseCase(
        logout: Logout,
        me: Me,
        getToken: GetToken,
        setProfilePicture: SetProfilePicture,
        isAuthenticated: IsAuthenticated,
        updateCustomer: UpdateCustomerUseCase,
        updateCourier: UpdateCourierUseCase,
        updateShop: UpdateShopUseCase
    ) =
        Profile(
            me,
            logout,
            getToken,
            setProfilePicture,
            isAuthenticated,
            updateCustomer,
            updateCourier,
            updateShop
        )

    @Provides
    @Singleton
    fun provideOrderUseCase(
        addOrder: AddOrder,
        deleteOrder: DeleteOrder,
        getAllOrders: GetAllOrders,
        getOrderForId: GetOrderForId
    ) =
        Order(getAllOrders, getOrderForId, addOrder, deleteOrder)

    @Provides
    @Singleton
    fun provideFavProductUseCase(
        addFavProduct: AddFavProduct,
        deleteFavProduct: DeleteFavProduct,
        getFavProducts: GetFavProducts
    ) =
        FavoriteProduct(getFavProducts, deleteFavProduct, addFavProduct)


    @Provides
    @Singleton
    fun provideAddProduct(repository: ProductRepository) =
        AddProduct(repository)

    @Provides
    @Singleton
    fun provideUpdateProduct(repository: ProductRepository) =
        UpdateProduct(repository)

    @Provides
    @Singleton
    fun provideProductUseCase(
        addProduct: AddProduct,
        getAllProducts: GetAllProducts,
        setProductImage: SetProductImage,
        getProductForId: GetProductForId,
        updateProduct: UpdateProduct,
    ) = ProductUseCase(addProduct, getAllProducts, getProductForId, updateProduct, setProductImage)

    @Provides
    @Singleton
    fun provideUpdateOrderUseCase(repository: OrderRepository) = UpdateOrder(repository)

    @Provides
    @Singleton
    fun provideGetProductUseCase(
        repository: ProductRepository
    ) =
        GetProductForId(repository)

    @Provides
    @Singleton
    fun provideSchedulePickupUseCase(
        addSchedulePickup: AddSchedulePickup,
        getAllScheduledPickups: GetAllScheduledPickups,
        getSchedulePickupForId: GetSchedulePickupForId,
        updateSchedulePickup: com.triforce.malacprodavac.domain.use_case.schedulePickup.UpdateScheduledPickup
    ) =
        SchedulePickupUseCase(
            getAllScheduledPickups,
            getSchedulePickupForId,
            addSchedulePickup,
            updateSchedulePickup
        )

    @Provides
    @Singleton
    fun provideCreateCourier(repository: CourierRepository) = CreateCourier(repository)

    @Provides
    @Singleton
    fun provideGetCouriers(repository: CourierRepository) = GetCouriers(repository)

    @Provides
    @Singleton
    fun provideGetCourier(repository: CourierRepository) = GetCourier(repository)


    @Provides
    @Singleton
    fun provideCourierUseCase(
        createCourier: CreateCourier,
        getCourier: GetCourier,
        getCouriers: GetCouriers
    ) = CourierUseCase(createCourier, getCouriers, getCourier)

    @Provides
    @Singleton
    fun provideProductMediasRepositoryImpl(
        api: ProductMediasApi,
        db: MalacProdavacDatabase,
        sessionManager: SessionManager
    ): ProductMediasRepository = ProductMediasRepositoryImpl(api, db, sessionManager)

    @Provides
    @Singleton
    fun provideSetProductImage(repository: ProductMediasRepository) =
        SetProductImage(repository)

    @Provides
    @Singleton
    fun provideGetProductsUseCase(repository: ProductRepository) =
        GetAllProducts(repository)

    @Provides
    @Singleton
    fun provideFavShopUseCase(
        addFavShop: AddFavShop,
        deleteFavShop: DeleteFavShop,
        getFavShop: GetFavShop
    ) =
        FavShopUseCase(getFavShop, deleteFavShop, addFavShop)

    @Provides
    @Singleton
    fun provideCreateReview(repository: ReviewsRepository) =
        CreateReview(repository)

    @Provides
    @Singleton
    fun provideGetReviews(repository: ReviewsRepository) =
        GetReviews(repository)

    @Provides
    @Singleton
    fun provideGetReview(repository: ReviewsRepository) =
        GetReview(repository)

    @Provides
    @Singleton
    fun provideUpdateReview(repository: ReviewsRepository) =
        UpdateReview(repository)


    @Provides
    @Singleton
    fun provideReviewUseCase(
        createReview: CreateReview,
        getReviews: GetReviews,
        getReview: GetReview,
        updateReview: UpdateReview
    ) =
        ReviewUseCase(createReview, getReviews, getReview, updateReview)

    @Provides
    @Singleton
    fun provideCreateReviewReply(repository: ReviewRepliesRepository) =
        CreateReviewReply(repository)

    @Provides
    @Singleton
    fun provideGetReviewReplies(repository: ReviewRepliesRepository) =
        GetReviewReplies(repository)

    @Provides
    @Singleton
    fun provideGetReviewReply(repository: ReviewRepliesRepository) =
        GetReviewReply(repository)

    @Provides
    @Singleton
    fun provideReviewReplyUseCase(
        createReviewReply: CreateReviewReply,
        getReviewReplies: GetReviewReplies,
        getReviewReply: GetReviewReply
    ) =
        ReviewReplyUseCase(createReviewReply, getReviewReplies, getReviewReply)

    @Provides
    @Singleton
    fun provideMalacProdavacDatabase(
        app: Application,
        converters: RoomConverters
    ): MalacProdavacDatabase =
        Room.databaseBuilder(
            app,
            MalacProdavacDatabase::class.java,
            "malacprodavac.db"
        ).addTypeConverter(converters)
            .fallbackToDestructiveMigration()
            .build()

    /** NOTIFICATIONS **/
    @Provides
    @Singleton
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            Screen.NotificationScreen.DEEPLINK_URI.toUri(),
            context,
            MainActivity::class.java
        )

        val clickPendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(1, PendingIntent.FLAG_IMMUTABLE)
        }

        return NotificationCompat
            .Builder(context, R.string.notification_channel_id.toString())
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.logo_gradient)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(clickPendingIntent)
    }

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            R.string.notification_channel_id.toString(),
            "Novosti",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }

    @Provides
    @Singleton
    fun provideApplicationCoroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}