package com.example.kinderdorf.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.ToggleButton
import com.example.kinderdorf.R
import com.google.android.material.button.MaterialButtonToggleGroup


class CalendarFragment : Fragment() {
    lateinit var toggleGroup: MaterialButtonToggleGroup
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
        toggleGroup = view.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup);

        toggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked){
                when (checkedId) {
                    R.id.allEventsButton -> showToast("All Events Clicked")
                    R.id.bookedEventsButton -> showToast("Booked Events clicked")
                    R.id.openRequestButton -> showToast("Open Requests clicked")
                }

            }
        }
    }

    private fun showToast(str:String) {
        Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()
    }

}