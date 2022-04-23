package com.example.kinderdorf.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.example.kinderdorf.User
import com.example.kinderdorf.adapters.CreditsAdapter
import com.parse.FindCallback
import com.parse.ParseException
import com.parse.ParseQuery
import com.parse.ParseUser
import java.util.*


class ProfileFragment : Fragment() {

    var allTransactions: MutableList<Transactions> = mutableListOf()
    lateinit var creditAdapter: CreditsAdapter
    lateinit var creditHistory: RecyclerView
    lateinit var tvName: TextView
    lateinit var tvUserName: TextView
    lateinit var tvCredits: TextView
    lateinit var tvTimeSpent: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        creditHistory = view.findViewById(R.id.tvTransHistory)
        tvName = view.findViewById(R.id.tvName)
        tvUserName = view.findViewById(R.id.UserName)
        tvCredits = view.findViewById(R.id.tvCredits)
        tvTimeSpent = view.findViewById(R.id.tvTimeSpent)

        val firstName = ParseUser.getCurrentUser().get("firstName").toString()
        val lastName = ParseUser.getCurrentUser().get("lastName").toString()

        tvName.text = "$firstName $lastName"
        tvUserName.text =  ParseUser.getCurrentUser().get("username").toString()

        creditAdapter = CreditsAdapter(requireContext(), allTransactions)
        creditHistory.adapter = creditAdapter
        creditHistory.layoutManager = LinearLayoutManager(requireContext())

        queryTransactions()
    }

    private fun queryTransactions() {
        val query: ParseQuery<Transactions> = ParseQuery.getQuery(Transactions::class.java)

//        query.include(Transactions.KEY_USER_BUYER)
//        query.include(Transactions.KEY_USER_SELLER)
        query.addDescendingOrder("dateRequest")
        query.whereLessThanOrEqualTo("dateRequest", Date())
        query.setLimit(POST_LIMIT)
        query.findInBackground(object: FindCallback<Transactions>{
            override fun done(transactions:MutableList<Transactions>?, e:ParseException?){
                if(e!= null){
                    Log.e(TAG, "Error getting transactions")
                } else{
                    if( transactions!= null){
                        for (transaction in transactions){
//                            Log.i(TAG, "Party: " + transaction.getUserSeller()?.username )
                        }
                        allTransactions.addAll(transactions)
                        creditAdapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    companion object{
        const val TAG = "ProfileFragment"
        const val POST_LIMIT = 20
    }

}