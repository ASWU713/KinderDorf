package com.example.kinderdorf

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

//*** This class is the adapter for the CalendarFragment.kt that will bind transactions to its view

class PersCalendAdapter(val context: Context, val transactions: List<Transactions>)
    : RecyclerView.Adapter<PersCalendAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): PersCalendAdapter.ViewHolder {
        // specify the layout file to use for this item
        val view = LayoutInflater.from(context).inflate(R.layout.transaction_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersCalendAdapter.ViewHolder, position: Int) {
        //this gets the particular transaction requested
        val transaction = transactions.get(position)
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        // returns the total amount of transactions
        return transactions.size
    }
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        // initialize id's of textViews in transaction_post.xml
        val requestDateData: TextView
        val priceData: TextView
        val sellerData: TextView
        val buyerData: TextView

        init {
            requestDateData = itemView.findViewById(R.id.requestDateData)
            priceData = itemView.findViewById(R.id.priceData)
            sellerData = itemView.findViewById(R.id.sellerData)
            buyerData = itemView.findViewById(R.id.buyerData)
        }
        fun bind(transactions: Transactions){
            requestDateData.text = Transactions.KEY_DATE_REQUEST
            priceData.text = Transactions.KEY_PRICE
            sellerData.text = Transactions.KEY_USER_SELLER
            buyerData.text = Transactions.KEY_USER_BUYER

        }
    }
}