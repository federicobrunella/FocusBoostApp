package com.example.focusboostapp.home

import android.app.AlertDialog
import android.app.NotificationManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.focusboostapp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val viewModel =  ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance( requireActivity().application)).get(HomeViewModel::class. java)

        val windowInsetsController = WindowCompat.getInsetsController(requireActivity().window, requireActivity().window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_BARS_BY_SWIPE

        val notificationManager: NotificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Interrompere la sessione")
        alertDialog.setMessage("Abbandonando la lessione i progressi non saranno registrati")
        alertDialog.setPositiveButton("Abbandona", DialogInterface.OnClickListener {
                dialog, id ->
            dialog.dismiss()
            viewModel.stopTimer()
            //Disable Immersive Mode if enabled
            if(viewModel.AppSettings.settingsImmersiveMode)
                windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
            //Disable DND if enabled
            if(viewModel.AppSettings.settingsDND) {
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            }
        })
        alertDialog.setNegativeButton("Annulla", DialogInterface.OnClickListener {
                dialog, id ->
            dialog.dismiss()
        })

        viewModel.init(requireContext())

        viewModel.isRunning.observe(viewLifecycleOwner, Observer{newState ->
            if(newState) {
                binding.startStopButton.text = "Abbandona"
                binding.buttonDescTextView.text = "Premere per abbandonare la sessione"
            } else {
                binding.startStopButton.text = "Avvia"
                binding.buttonDescTextView.text = "Premere per avviare una sessione"
            }
        })

        binding.startStopButton.setOnClickListener {
            if(!viewModel.isRunning.value!!) {
                viewModel.startTimer(viewModel.getSettingsMillis(), 10)
                //Immersive Mode
                if(viewModel.AppSettings.settingsImmersiveMode)
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                //DND Mode
                if(viewModel.AppSettings.settingsDND) {
                    if (!notificationManager.isNotificationPolicyAccessGranted()) {
                        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                        startActivity(intent)
                    }
                    notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                }
            }
            else
                alertDialog.show()
        }
        binding.viewModel= viewModel
        binding.lifecycleOwner= this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}