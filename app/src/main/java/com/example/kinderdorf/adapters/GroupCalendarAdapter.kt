package com.example.kinderdorf.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.Message
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.example.kinderdorf.adapters.GroupCalendarAdapter.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GroupCalendarAdapter(val context: Context, val transactions: ArrayList<Transactions>)
    : RecyclerView.Adapter<ViewHolder>()
    {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(context).inflate(R.layout.item_group_calendar, parent, false)
            return ViewHolder(view)

        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val transaction = transactions[position]
            holder.bind(transaction)
        }

        override fun getItemCount(): Int {
            return transactions.size
        }


        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvUserSeller: TextView
            val tvRequestDate: TextView
            val tvRequestTime: TextView
            val tvPrice: TextView
            val tvBuyer: TextView


            init {
                tvUserSeller = itemView.findViewById(R.id.tvUserSeller)
                tvRequestDate = itemView.findViewById(R.id.tvRequestDate)
                tvRequestTime = itemView.findViewById(R.id.tvRequestTime)
                tvPrice = itemView.findViewById(R.id.tvPrice)
                tvBuyer = itemView.findViewById(R.id.tvBuyers)

            }

            fun bind(transaction: Transactions) {
                val simpleDateFormat = SimpleDateFormat("MMM dd")
                val simpleTimeFormat = SimpleDateFormat("h:mma")

                tvUserSeller.text = "By: " + transaction.getUserSeller()?.get("firstName").toString() + " " + transaction.getUserSeller()?.get("lastName").toString().get(0)
                tvRequestDate.text = simpleDateFormat.format(transaction.getDateRequest()).toString()
                tvRequestTime.text = "@" + simpleTimeFormat.format(transaction.getDateRequest()).toString()
                tvPrice.text = "\$KD:" + transaction.getPrice()?.toInt().toString()
                tvBuyer.text = "Accepted: " + transaction.getUserBuyer()?.get("firstName").toString() + " " + transaction.getUserBuyer()?.get("lastName").toString().get(0)
            }

        }



        fun addAll(transactionList: List<Transactions>) {
            transactions.addAll(transactionList)
            notifyDataSetChanged()
        }

        fun clear(transactionList: List<Transactions>) {
            transactions.clear()
            notifyDataSetChanged()
        }


    }