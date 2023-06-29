package com.example.focusboostapp.stats

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusboostapp.R
import com.example.focusboostapp.databinding.FragmentStatsDetailBinding
import com.google.firebase.Timestamp
import java.sql.Time
import java.time.LocalDateTime

class StatsDetailFragment : Fragment() {
    private lateinit var adapter: StatsDetailAdapter
    private lateinit var statsDetailArrayList: ArrayList<Timestamp>


    private val args : StatsDetailFragmentArgs by navArgs<StatsDetailFragmentArgs>()
    private var _binding: FragmentStatsDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = args.statsDetailArg

        // getting the recyclerview
        val recyclerview = binding.recyclerview
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        if(arg.DND){
            binding.textViewDndContent.text= "Enabled"
            binding.textViewDndContent.setTextColor((Color.parseColor("#008000")))
        }else{
            binding.textViewDndContent.text= "Disabled"
            binding.textViewDndContent.setTextColor((Color.parseColor("#ff0000")))
        }

        if(arg.completedSession){
            binding.textViewCompletedSessionContent.text= "Completed"
            binding.textViewCompletedSessionContent.setTextColor((Color.parseColor("#008000")))
        }else{
            binding.textViewCompletedSessionContent.text= "Failed"
            binding.textViewCompletedSessionContent.setTextColor((Color.parseColor("#ff0000")))
        }

        if(arg.immersiveMode){
            binding.textViewImmersiveModeContent.text= "Enabled"
            binding.textViewImmersiveModeContent.setTextColor((Color.parseColor("#008000")))
        }else{
            binding.textViewImmersiveModeContent.text= "Disabled"
            binding.textViewImmersiveModeContent.setTextColor((Color.parseColor("#ff0000")))
        }
        binding.textViewSessionLengthContent.text= String.format ("%02d:%02d:%02d", arg.sessionLengthHours, arg.sessionLengthMinutes, arg.sessionLengthSeconds)
        binding.textViewStartDateTimeContent.text= arg.startDateTime
        binding.textViewStopDateTimeContent.text= arg.stopDateTime

        /*binding.back.setOnClickListener {
            view.findNavController().navigate(R.id.action_statsDetail_to_stats)
        }*/

        if(arg.advancedSettings == false) {
            binding.advancedStatsTitleTextView.text = "Advanced Stats Disabled!"
            //Toast.makeText(requireContext(), "No advanced Stats Available!", Toast.LENGTH_SHORT).show()
        }else{
            statsDetailArrayList = arrayListOf()

            if (arg.userDistractions != null) {
                    statsDetailArrayList.addAll(arg.userDistractions)
                    statsDetailArrayList.add(Timestamp.now())
                    statsDetailArrayList.add(0, Timestamp.now())
                }
            // This will pass the ArrayList to our Adapter
            adapter = StatsDetailAdapter(statsDetailArrayList)
            // Setting the Adapter with the recyclerview
            recyclerview.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}