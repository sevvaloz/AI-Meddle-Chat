package com.sevvalozdamar.aimeddlechat.ui.chatdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.sevvalozdamar.aimeddlechat.R
import com.sevvalozdamar.aimeddlechat.databinding.FragmentChatDetailBinding
import com.sevvalozdamar.aimeddlechat.model.firebase.Chatroom
import com.sevvalozdamar.aimeddlechat.model.firebase.Message
import com.sevvalozdamar.aimeddlechat.model.chatgptrequest.BaseRequest
import com.sevvalozdamar.aimeddlechat.repository.FirebaseRepository
import com.sevvalozdamar.aimeddlechat.utils.ChatDetailHelper.createPrompt
import com.sevvalozdamar.aimeddlechat.utils.ChatDetailHelper.jsonToSentiment
import com.sevvalozdamar.aimeddlechat.utils.ChatDetailHelper.parseResponseToSuggestionMessages
import com.sevvalozdamar.aimeddlechat.utils.ChatDetailHelper.removeOuterBrackets
import com.sevvalozdamar.aimeddlechat.utils.viewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatDetailFragment : Fragment(R.layout.fragment_chat_detail) {

    private val binding by viewBinding(FragmentChatDetailBinding::bind)
    private val viewModel: SentimentViewModel by viewModels()
    private val viewModelGPT: ChatGptViewModel by viewModels()
    private val args by navArgs<ChatDetailFragmentArgs>()
    private val adapter = SuggestionMessagesAdapter()
    private var chatroomId: String? = null
    private var chatroom: Chatroom? = null
    private var otherUserId: String? = null
    private var listenerRegistration: ListenerRegistration? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSuggestionMessages.adapter = adapter
        otherUserId = args.chatroomId.substringAfter('_')
        chatroomId = FirebaseRepository.getChatroomId(FirebaseRepository.currentUserId().toString(), otherUserId!!)
        println("ChatDetail Other User Id: " + otherUserId.toString())
        println("ChatDetail Chatroom Id: " + chatroomId.toString())


        with(binding) {
            FirebaseRepository.getNewUserDetails(otherUserId!!).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null) {
                            val userName = document.getString("userName")
                            tvUserName.text = userName
                        }
                    }
                }

            ivBack.setOnClickListener {
                val fragmentManager = requireActivity().supportFragmentManager
                fragmentManager.popBackStack()
            }

            btnSendMessage.setOnClickListener(View.OnClickListener {
                val message: String = etMessageInput.text.toString().trim { it <= ' ' }
                if (message.isEmpty()) return@OnClickListener
                sendMessageToUser(message)
            })
        }

        getOrCreateChatroom()
        setupChatAdapter()

        observe()

        lifecycleScope.launch {
            getLastMessageFromChatroom()
        }

    }

    private suspend fun getLastMessageFromChatroom() {
        val chatroomMessagesRef = FirebaseRepository.getChatroomMessageDetails(chatroomId.toString())
            .orderBy("timestamp", Query.Direction.DESCENDING)

        try {
            listenerRegistration = chatroomMessagesRef.addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Log.d("BAK", "Error listening for chatroom updates: ${exception.message}")
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val lastMessage = snapshot.documents[0].toObject(Message::class.java)
                    if(lastMessage?.senderId != FirebaseRepository.currentUserId()){
                        lastMessage?.let {
                            GlobalScope.launch {
                                delay(10000)
                                getSentimentResult(it.messageText.toString())
                                //delay(5000)
                                getSuggestionMessages(createPrompt(it.messageText.toString()))
                                Log.d("BAK", "Last message of other person in the chatroom: ${it.messageText}")
                            }
                        }
                    }
                } else {
                    Log.d("BAK", "getLastMessageFromChatsCollection Empty Collection")
                }
            }

        } catch (e: Exception) {
            Log.d("BAK", "getLastMessageFromChatsCollection Error: ${e.message}")
        }
    }

    private fun getSentimentResult(sentence: String) {
        viewModel.getSentimentResult(sentence)
    }

    private fun getSuggestionMessages(prompt: String){
        val request = BaseRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(com.sevvalozdamar.aimeddlechat.model.chatgptrequest.Message(prompt, "user")),
            temperature = 0.7
        )
        viewModelGPT.getSuggestionMessages(request)
    }

    private fun observe(){
        viewModel.result.observe(viewLifecycleOwner){
            val sentiment = jsonToSentiment(removeOuterBrackets(it))
            Log.d("BAK", "Sentiment of the last message: $sentiment")



        }
        viewModelGPT.result.observe(viewLifecycleOwner){
            //Log.d("BAK2", "ChatGPT Result: ${it.choices[0].message.content}")
            val suggestionMessages = parseResponseToSuggestionMessages(it.choices[0].message.content)
            adapter.submitList(suggestionMessages)
        }
    }

    private fun getOrCreateChatroom() {
        FirebaseRepository.getChatroomDetails(otherUserId!!).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                chatroom = task.result.toObject(Chatroom::class.java)
                if (chatroom == null) {
                    //first time chat
                    chatroom = Chatroom(
                        chatroomId = chatroomId,
                        userIds = listOf(FirebaseRepository.currentUserId().toString(), otherUserId!!),
                        lastMessageTimestamp = Timestamp.now(),
                        lastMessageSenderId = ""
                    )
                    FirebaseRepository.getChatroomDetails(chatroomId.toString()).set(chatroom!!)
                }
            }
        }
    }

    private fun sendMessageToUser(message: String?) {
        chatroom?.lastMessageTimestamp = Timestamp.now()
        chatroom?.lastMessageSenderId = FirebaseRepository.currentUserId()!!

        FirebaseRepository.getChatroomDetails(chatroomId!!).set(chatroom!!)
        val message = Message(
            messageId = FirebaseRepository.getMessageId(
                FirebaseRepository.currentUserId().toString(),
                otherUserId!!
            ),
            messageText = message,
            senderId = FirebaseRepository.currentUserId(),
            timestamp = Timestamp.now(),
            sentiment = null
        )
        FirebaseRepository.getChatroomMessageDetails(chatroomId!!).add(message)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.etMessageInput.setText("")
                }
            }
    }

    private fun setupChatAdapter() {
        with(binding) {
            val query = FirebaseRepository.getChatroomMessageDetails(chatroomId.toString())
                .orderBy("timestamp", Query.Direction.ASCENDING)

            val options = FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message::class.java)
                .build()

            val adapter = MessagesAdapter(options)
            rvMessages.adapter = adapter
            adapter.startListening()

            adapter.registerAdapterDataObserver(object : AdapterDataObserver() {
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    rvMessages.smoothScrollToPosition(0)
                }
            })

        }
    }

}