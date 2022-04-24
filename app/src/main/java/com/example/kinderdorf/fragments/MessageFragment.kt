package com.example.kinderdorf.fragments

import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.Message
import com.example.kinderdorf.R
import com.example.kinderdorf.User
import com.example.kinderdorf.adapters.ChatAdapter
import com.parse.*
import java.util.*
import kotlin.collections.ArrayList


class MessageFragment : Fragment() {

    lateinit var etMessage: EditText
    lateinit var ibSend: ImageButton

    lateinit var messagesRV: RecyclerView
//    lateinit var adapter: MessageAdapter
    lateinit var adapter: ChatAdapter
    var allMessages: ArrayList<Message> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        messagesRV = view.findViewById(R.id.rvMessages)
//        adapter = MessageAdapter(requireContext(), allMessages)
        adapter = ChatAdapter(requireContext(), ParseUser.getCurrentUser().objectId, allMessages)
        messagesRV.adapter = adapter
        var linearLayout: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayout.setReverseLayout(true)
        messagesRV.layoutManager = linearLayout



        queryMessages()
//        startWithCurrentUser()
        setupMessagePosting()
    }

    private fun queryMessages( ) {
        val query: ParseQuery<Message> = ParseQuery.getQuery(Message::class.java)
        query.include(Message.KEY_USER)
        query.limit = 50
        query.orderByDescending("createdAt")
        query.findInBackground(object : FindCallback<Message> {
            override fun done(messages: MutableList<Message>?, e: ParseException?) {

                if (e != null) {
                    Log.e(TAG, "Error fetching messages")
                    e.printStackTrace()
                } else {
                    Log.i(TAG, "No Errors fetching messages")
                    if (messages != null) {
                        for (message in messages) {
                            Log.i(TAG, "Message: " + message.getMessageText() + " , username: " + message.getUser()?.username)
                        }
                        allMessages.clear()
                        allMessages.addAll(messages)
                        adapter.notifyDataSetChanged()

                        messagesRV.scrollToPosition(0);

                    }

                }
            }
        })
    }

    fun startWithCurrentUser() {

        setupMessagePosting()
    }

    fun setupMessagePosting() {
        // Find the text field and button
        etMessage = view?.findViewById<TextView>(R.id.etMessage) as EditText
        ibSend = view?.findViewById<View>(R.id.ibSend) as ImageButton

        // When send button is clicked, create message object on Parse
        ibSend!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val data: String = etMessage!!.getText().toString()
                val message = ParseObject.create("Chat")

                message.put(Message.KEY_USER, ParseUser.getCurrentUser())
                message.put(Message.KEY_MESSAGETEXT, data)
                message.put(Message.KEY_DATE, Date())
                message.saveInBackground(object : SaveCallback {
                    override fun done(e: ParseException?) {
                        if (e == null) {
                            Toast.makeText(
                                activity, "Successfully created message on Parse",
                                Toast.LENGTH_SHORT
                            ).show()
                            queryMessages()
                        } else {
                            Log.e(TAG, "Failed to save message", e)
                        }
                    }
                })
                etMessage!!.setText(null)
            }
        })
    }




    companion object {
        const val TAG = "MESSAGE_FRAGMENT"
    }


}