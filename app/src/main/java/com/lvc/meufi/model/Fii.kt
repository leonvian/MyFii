package com.lvc.meufi.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Fii(
    @PrimaryKey val code: String,
    val name: String,
    val type: FiiType
)