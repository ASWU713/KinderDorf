package com.example.kinderdorf.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions

class ProfileAdapter(val context: Context, val transactions: List<Transactions>)
    : RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_transactions, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions.get(position)
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class ViewHolder(itemView: View) :  RecyclerView.ViewHolder(itemView) {
        val etRequestDate: TextView
//        val tvParty: TextView

        init {
            etRequestDate = itemView.findViewById(R.id.etRequestDate)
//            tvParty = itemView.findViewById(R.id.tvParty)
        }

        fun bind(transactions: Transactions) {
            etRequestDate.text = transactions.getDateRequest().toString()
        }


    }

}