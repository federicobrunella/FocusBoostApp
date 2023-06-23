package com.example.focusboostapp.stats


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.focusboostapp.databinding.FragmentStatsBinding
import com.example.focusboostapp.settings.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot


class StatsFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var statsArrayList: ArrayList<Stats>
    private lateinit var adapter: StatsAdapter
    private lateinit var db: FirebaseFirestore


    private var _binding: FragmentStatsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        // getting the recyclerview
        val recyclerview = binding.recyclerview
        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(requireContext())

        statsArrayList = arrayListOf()

        // This will pass the ArrayList to our Adapter
        adapter = StatsAdapter(statsArrayList)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        EventChangeListener()

        //binding.statsViewModel = viewModel
        //binding.lifecycleOwner= this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun EventChangeListener(){
        db = FirebaseFirestore.getInstance()
        db.collection("sessions")
            .whereEqualTo("user", firebaseAuth.currentUser?.email)
            .addSnapshotListener(object: EventListener<QuerySnapshot>{
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if(error != null)
                    {
                        Log.e("Firestore error", error.message.toString())
                    }

                    for( dc: DocumentChange in value?.documentChanges!!)
                    {
                        if(dc.type == DocumentChange.Type.ADDED)
                            statsArrayList.add(dc.document.toObject(Stats::class.java))
                    }

                    adapter.notifyDataSetChanged()
                }


            })
    }
}