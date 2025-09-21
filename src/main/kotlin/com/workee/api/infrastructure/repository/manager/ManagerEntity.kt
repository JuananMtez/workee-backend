package com.workee.api.infrastructure.repository.manager

import com.workee.api.infrastructure.repository.user.UserEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "manager", schema = "public")
data class ManagerEntity(
    @Id
    val id: UUID,

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime

)