package com.triforce.malacprodavac.presentation.notifications

import com.triforce.malacprodavac.domain.model.notifications.Notification

data class NotificationsState(
    val notifications: List<Notification> = emptyList(),
    val isLoading: Boolean = false,
    val currentPage: Int = 1,
    val isLastPage: Boolean = false,
    val perPage: Int = 20
)
