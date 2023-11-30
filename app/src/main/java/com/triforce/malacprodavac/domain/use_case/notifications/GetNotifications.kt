package com.triforce.malacprodavac.domain.use_case.notifications

import com.triforce.malacprodavac.domain.repository.notifications.NotificationsRepository

data class GetNotifications(val repository: NotificationsRepository) {
    suspend operator fun invoke() = repository.getNotifications()
}
