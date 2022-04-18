package com.example.kinderdorf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kinderdorf.Message
import com.example.kinderdorf.R
import com.example.kinderdorf.adapters.MessageAdapter.*

class MessageAdapter(val context: Context, val messages: ArrayList<Message>)
    :RecyclerView.Adapter<ViewHolder>(){

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView
        val tvUsername: TextView
//        val ivProfileImage: ImageView

        init {
            tvMessage = itemView.findViewById(R.id.tvMessageIn)
            tvUsername = itemView.findViewById(R.id.tvUsernameIn)
//            ivProfileImage = itemView.findViewById(R.id.ivUserIn)
        }

        fun bind(message: Message) {
            tvMessage.text = message.getMessageText()
            tvUsername.text = message.getUser()?.username
//            Glide.with(itemView.context).load(R.drawable.noprofileimage).into(ivProfileImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_message_incoming, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addAll(messageList: List<Message>) {
        messages.addAll(messageList)
        notifyDataSetChanged()
    }



}