package com.scholar.domain.model

interface Rate {
    val rate: Int
    val comment: String?
}


data class RateNetwork(
    override val rate: Int,
    override val comment: String?,
) : Rate