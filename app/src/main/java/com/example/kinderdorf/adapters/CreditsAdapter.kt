package com.example.kinderdorf.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.example.kinderdorf.User
//import com.example.kinderdorf.fragments.ProfileFragment.Companion.TAG
import java.text.SimpleDateFormat

class CreditsAdapter(val context: Context, val transactions: List<Transactions>)
    : RecyclerView.Adapter<CreditsAdapter.ViewHolder>() {

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
        val tvRequestDate: TextView
        val tvCredits: TextView
        val simpleDateFormat = SimpleDateFormat("MMM-dd-yyyy")
//        val tvFirstName: TextView
//        val tvLastName: TextView
//        val tvUserName: TextView

        init {
            tvRequestDate = itemView.findViewById(R.id.tvRequestDate)
            tvCredits = itemView.findViewById(R.id.tvCredits)

//            tvFirstName = itemView.findViewById(R.id.tvFirstName)
//            tvLastName = itemView.findViewById(R.id.tvLastName)
//            tvUserName = itemView.findViewById(R.id.UserName)
        }

        fun bind(transactions: Transactions) {
            Log.i(TAG, "DAte:"+ simpleDateFormat.format(transactions.getDateRequest()))
            tvRequestDate.text = simpleDateFormat.format(transactions.getDateRequest()).toString()
            tvCredits.text = transactions.getPrice().toString()
//            tvFirstName.text = user.getFirstName().toString()
//            tvLastName.text = user.getLastName().toString()
//            tvUserName.text = user.getUser().toString()
        }


    }

    companion object {
        const val TAG = "CreditsAdapter"
    }

}