package com.example.autoclickerapp.model

data class Identifier(
    val databaseId: Long = DATABASE_ID_INSERTION,
    val tempId: Long? = null,
) {

    constructor(id: Long, asTemporary: Boolean = false) : this(
        databaseId = if (asTemporary) DATABASE_ID_INSERTION else id,
        tempId = if (asTemporary) id else null,
    )

    /** Ensure correctness of ids. */
    init {
        if (databaseId == DATABASE_ID_INSERTION && tempId == null)
            throw IllegalArgumentException("DomainId must be set when using db id 0")

        if (databaseId != DATABASE_ID_INSERTION && tempId != null)
                throw IllegalArgumentException("Both ids can't be set")
    }

    fun isInDatabase(): Boolean = databaseId != DATABASE_ID_INSERTION
}

/** Value to set to [Identifier.databaseId] when willing to insert an item in database. */
const val DATABASE_ID_INSERTION = 0L