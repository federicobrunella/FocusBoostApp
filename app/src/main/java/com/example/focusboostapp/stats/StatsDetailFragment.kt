package com.example.focusboostapp.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.focusboostapp.R
import com.example.focusboostapp.databinding.FragmentStatsDetailBinding

class StatsDetailFragment : Fragment() {
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

        val arg = args.StatsDetailArg

        binding.textView2.text = arg.startDateTime

        binding.back.setOnClickListener {
            view.findNavController().navigate(R.id.action_statsDetail_to_stats)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}