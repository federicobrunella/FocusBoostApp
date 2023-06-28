package com.example.focusboostapp.stats

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusboostapp.R
import com.example.focusboostapp.databinding.FragmentStatsDetailBinding
import com.google.firebase.Timestamp
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

        val timeline = binding.recyclerview
        val arg = args.StatsDetailArg

        // getting the recyclerview
        val recyclerview = binding.recyclerview
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        binding.textViewDndContent.text= arg.DND.toString()
        binding.textViewCompletedSessionContent.text= arg.completedSession.toString()
        binding.textViewImmersiveModeContent.text= arg.immersiveMode.toString()
        binding.textViewSessionLengthContent.text= String.format ("%02d:%02d:%02d", arg.sessionLengthHours, arg.sessionLengthMinutes, arg.sessionLengthSeconds)
        binding.textViewStartDateTimeContent.text= arg.stopDateTime

        binding.back.setOnClickListener {
            view.findNavController().navigate(R.id.action_statsDetail_to_stats)
        }

        statsDetailArrayList = arrayListOf()
        //Log.i("distractions", arg.userDistractions.toString())
        Log.i("distractions", statsDetailArrayList.toString())

        if (arg.userDistractions != null) {
                statsDetailArrayList.addAll(arg.userDistractions)
            }
        // This will pass the ArrayList to our Adapter
        adapter = StatsDetailAdapter(statsDetailArrayList)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}