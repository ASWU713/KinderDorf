package com.example.kinderdorf.adapters

import com.example.kinderdorf.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.Message
import java.lang.IllegalArgumentException


class ChatAdapter(context: Context, userId: String, messages: List<Message>) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder?>() {
    private var mMessages: List<Message>
    private val mContext: Context
    private val mUserId: String
    private val MESSAGE_OUTGOING = 123
    private val MESSAGE_INCOMING = 321

    init {
        mMessages = messages
        mUserId = userId
        mContext = context
    }

    override fun getItemViewType(position: Int): Int {
        return if (isMe(position)) {
            MESSAGE_OUTGOING
        } else {
            MESSAGE_INCOMING
        }
    }

    private fun isMe(position: Int): Boolean {
        val message = mMessages[position]
        return message.getUserId() != null && message.getUserId().equals(mUserId)
    }

    override fun getItemCount(): Int {
        return mMessages.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder {
        var context: Context = parent.getContext()
        var inflater: LayoutInflater = LayoutInflater.from(context)

        if (viewType == MESSAGE_INCOMING) {
            var contactView: View = inflater.inflate(R.layout.item_message_incoming, parent, false)
            return IncomingMessageViewHolder(contactView)
        } else if (viewType == MESSAGE_OUTGOING) {
            var contactView: View = inflater.inflate(R.layout.item_message_outgoing, parent, false)
            return OutgoingMessageViewHolder(contactView)
        } else {
            throw IllegalArgumentException("Unknown View Type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        var message: Message = mMessages[position]
        holder.bindMessage(message)
    }

    abstract class MessageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        abstract fun bindMessage(message: Message?)
    }

    class IncomingMessageViewHolder(itemView: View) :
        MessageViewHolder(itemView) {
//        var imageOther: ImageView
        var body: TextView
        var name: TextView
        override fun bindMessage(message: Message?) {
            body.text = message?.getMessageText()
            name.text = message?.getUser()?.get("firstName").toString()[0].toString() +  message?.getUser()?.get("lastName").toString()[0].toString()
        }

        init {

//            imageOther = itemView.findViewById<View>(R.id.ivProfileOther) as ImageView
            body = itemView.findViewById(R.id.tvMessageIn)
            name = itemView.findViewById(R.id.tvUsernameIn)
        }
    }

    class OutgoingMessageViewHolder(itemView: View) :
        MessageViewHolder(itemView) {
//        var imageMe: ImageView
        var body: TextView
        var name: TextView
        override fun bindMessage(message: Message?) {
            body.text = message?.getMessageText()
            name.text = message?.getUser()?.get("firstName").toString()[0].toString() +  message?.getUser()?.get("lastName").toString()[0].toString()
        }

        init {
//            imageMe = itemView.findViewById<View>(R.id.ivProfileMe) as ImageView
            body = itemView.findViewById(R.id.tvMessageOut)
            name = itemView.findViewById(R.id.tvUsernameOut)
        }
    }
}