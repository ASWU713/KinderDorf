package com.example.kinderdorf.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kinderdorf.R
import com.example.kinderdorf.Transactions
import com.parse.ParseQuery


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        queryTransactions()
    }

    private fun queryTransactions() {
        val query: ParseQuery<Transactions> = ParseQuery.getQuery(Transactions::class.java)


    }


}