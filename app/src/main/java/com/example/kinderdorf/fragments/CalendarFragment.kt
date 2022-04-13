package com.example.kinderdorf.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.PersCalendAdapter
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.google.android.material.button.MaterialButtonToggleGroup
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser


class CalendarFragment : Fragment() {
    lateinit var toggleGroup: MaterialButtonToggleGroup
    lateinit var calendarRecyclerView: RecyclerView
    var allTransactions: MutableList<Transactions> = mutableListOf()

    lateinit var adapter: PersCalendAdapter

// tells the fragment which layout to use
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // set onClickListeners and handle logic
        toggleGroup = view.findViewById(R.id.toggleButtonGroup);
        calendarRecyclerView = view.findViewById(R.id.calendarRecyclerList)

        // this identifies which toggle button is clicked, and what data to pull from parse
        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when (checkedId) {
                    R.id.allEventsButton -> showToast("All Events Clicked")
                    R.id.bookedEventsButton -> showToast("Booked Events clicked")
                    R.id.openRequestButton -> showToast("Open Requests clicked")
                }

            }
        }

        // steps for populating the Recyclerview
        //1. Create a layout for each row in the list (booked and open appointments)
        //2. Create a data source for each row (The Transaction Class)
        //3. Create adapter that will bridge data and row layout
        //4. Set adapter on RecyclerView
        adapter = PersCalendAdapter(requireContext(), allTransactions)
        calendarRecyclerView.adapter = adapter
        //5. Set layout manager on RecyclerView
        calendarRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        queryTransactions()
    }

    private fun showToast(str:String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

     fun queryTransactions(){
        // specify which class to query
        val query: ParseQuery<Transactions> = ParseQuery.getQuery(Transactions::class.java)
        // find all the Transaction objects in our server
        query.include(Transactions.KEY_DATE_REQUEST)
        // return the transactions in descending order
        query.addDescendingOrder("dateRequest")

        query.findInBackground(object : FindCallback<Transactions> {
            override fun done(transactions: MutableList<Transactions>?, e:ParseException?) {
                if (e != null) {
                    // something went wrong
                    Log.e(TAG, "Error fetching transactions")
                } else {
                    if (transactions != null){
                        for (transaction in transactions) {
                            Log.i(
                                TAG, "Transaction:" + transaction.getDateRequest()+", seller: "+
                                        transaction.getUserBuyer()?.username)
                        }
                        allTransactions.addAll(transactions)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
    companion object {
        const val TAG="CalendarFragment"
    }
}