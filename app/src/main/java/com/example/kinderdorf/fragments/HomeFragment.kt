package com.example.kinderdorf.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.example.kinderdorf.adapters.GroupCalendarAdapter
import com.google.android.material.button.MaterialButtonToggleGroup
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var groupCalRV : RecyclerView
    lateinit var adapter: GroupCalendarAdapter
    var allTransactions: ArrayList<Transactions> = ArrayList()
    lateinit var toggleGroup: MaterialButtonToggleGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupCalRV = view.findViewById(R.id.rvGroupCalendar)
        adapter = GroupCalendarAdapter(requireContext(), allTransactions)
        groupCalRV.adapter = adapter
        groupCalRV.layoutManager = LinearLayoutManager(requireContext())
        toggleGroup = view.findViewById(R.id.homeToggleButtonGroup)
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when(checkedId) {
                    R.id.scheduledButton -> {
                        Toast.makeText(requireContext(), "Scheduled Button Clicked", Toast.LENGTH_SHORT).show()
                        queryScheduledTransactions()
                    }
                    R.id.openButton -> {
                        Toast.makeText(requireContext(), "Open Button Clicked", Toast.LENGTH_SHORT).show()
                        queryOpenTransactions()
                    }
                }
            }
        }


        queryScheduledTransactions()

    }

    open fun queryScheduledTransactions() {
        val query: ParseQuery<Transactions> = ParseQuery.getQuery(Transactions::class.java)
        query.limit = 50
        query.include("sellerUser")
        query.include("buyerUser")
        query.orderByAscending("dateRequest")
        query.whereExists("sellerUser")
        query.whereExists("buyerUser")
//        query.whereGreaterThanOrEqualTo("dateRequest", Date())
        query.findInBackground(object : FindCallback<Transactions> {
            override fun done(transactions: MutableList<Transactions>?, e: ParseException?) {

                if (e != null) {
                    Log.e(TAG, "Error fetching transactions")
                    e.printStackTrace()
                } else {
                    Log.i(TAG, "No errors fetching transactions")
                    Log.i(TAG, "Transaction Size: " + transactions?.size.toString())
                    if (transactions != null) {
                        for (transaction in transactions) {
                            var list: List<String> = transaction.getDateRequest().toString().split(" ")
                            Log.i(TAG, "Seller: " + transaction.getUserSeller()?.get("firstName") +
                                            "Date Requested: " + list[1] + "-" + list[2] +
                                            "Price: " + transaction.getPrice() +
                                            "Buyer: " + transaction.getUserBuyer()?.username
                            )

                        }
                        allTransactions.clear()
                        allTransactions.addAll(transactions)
                        adapter.notifyDataSetChanged()

                    }
                }
            }
        })
    }

    open fun queryOpenTransactions() {
        val query: ParseQuery<Transactions> = ParseQuery.getQuery(Transactions::class.java)
        query.limit = 50
        query.include("sellerUser")
        query.include("buyerUser")
        query.orderByAscending("dateRequest")
        query.whereExists("sellerUser")
        query.whereDoesNotExist("buyerUser")
//        query.whereGreaterThanOrEqualTo("dateRequest", Date())
        query.findInBackground(object : FindCallback<Transactions> {
            override fun done(transactions: MutableList<Transactions>?, e: ParseException?) {

                if (e != null) {
                    Log.e(TAG, "Error fetching transactions")
                    e.printStackTrace()
                } else {
                    Log.i(TAG, "No errors fetching transactions")
                    Log.i(TAG, "Transaction Size: " + transactions?.size.toString())
                    if (transactions != null) {
                        for (transaction in transactions) {
                            var list: List<String> = transaction.getDateRequest().toString().split(" ")
                            Log.i(TAG, "Seller: " + transaction.getUserSeller()?.username +
                                    "Date Requested: " + list[1] + "-" + list[2] +
                                    "Price: " + transaction.getPrice() +
                                    "Buyer: " + transaction.getUserBuyer()?.username
                            )

                        }
                        allTransactions.clear()
                        allTransactions.addAll(transactions)
                        adapter.notifyDataSetChanged()

                    }
                }
            }
        })
    }

//    open fun noEventsTransaction():Transactions {
//        var transact: Transactions = Transactions()
//
//    }

    companion object {
        const val TAG = "HOME_FRAGMENT"
    }

}