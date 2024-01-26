package com.sevvalozdamar.aimeddlechat.model.firebase

import com.google.firebase.Timestamp

data class User(
    var userId: String? = "",
    var userPhone: String? = "",
    var userName: String? = "",
    var createdTimestamp: Timestamp? = null
)
