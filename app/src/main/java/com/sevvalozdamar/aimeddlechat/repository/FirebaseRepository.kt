package com.sevvalozdamar.aimeddlechat.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

object FirebaseRepository {

    fun currentUserId(): String? = FirebaseAuth.getInstance().currentUser?.uid

    fun isCurrentUserExist() = FirebaseAuth.getInstance().currentUser != null

    fun firebaseAuth() = Firebase.auth

    fun getCurrentUserDetails(): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId()!!)
    }

    fun getAllUserDetails(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("users")
    }

    fun getNewUserDetails(newUserId: String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("users").document(newUserId)
    }

    fun getChatroomDetails(chatroomId: String): DocumentReference {
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId)
    }

    fun getChatDetails(chatroomId: String, messageId: String): DocumentReference{
        return getChatroomDetails(chatroomId).collection("chats").document(messageId)
    }

    fun getChatroomMessageDetails(chatroomId: String): CollectionReference {
        return getChatroomDetails(chatroomId).collection("chats")
    }

    fun timestampToDate(timestamp: Timestamp): String? {
        return SimpleDateFormat("dd/MM/yyyy , HH:MM", Locale.getDefault()).format(timestamp.toDate())
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun getChatroomId(userId1: String, userId2: String): String {
        return if (userId1.hashCode() < userId2.hashCode()) {
            userId1 + "_" + userId2
        } else {
            userId2 + "_" + userId1
        }
    }

    fun getMessageId(userId1: String, userId2: String): String {
        return userId1 + "_" + userId2 + "_" + Timestamp.now()
    }

    fun getOtherUserFromChatroom(userIds: List<String>): DocumentReference {
        return if (userIds[0] == currentUserId()) {
            getAllUserDetails().document(userIds[1])
        } else {
            getAllUserDetails().document(userIds[0])
        }
    }

    fun getAllChatroomCollection(): CollectionReference {
        return FirebaseFirestore.getInstance().collection("chatrooms")
    }
}