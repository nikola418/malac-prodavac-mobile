package com.triforce.malacprodavac.data.local.customers

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.triforce.malacprodavac.data.local.user.UserEntity

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId")
    )]
)
data class CustomerEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val updatedAt: String,
    val createdAt: String,
) {
    @Ignore
    val user: UserEntity? = null
}
