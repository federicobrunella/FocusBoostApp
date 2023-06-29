package com.example.focusboostapp.settings

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.focusboostapp.R
import com.example.focusboostapp.SignInActivity
import com.example.focusboostapp.databinding.FragmentSettingsBinding
import com.example.focusboostapp.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentSettingsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel =  ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance( requireActivity().application)).get(SettingsViewModel::class. java)
        viewModel.init(requireContext())

        val DNDSwitch = binding.DNDSwitch
        DNDSwitch.setChecked(viewModel.AppSettings.settingsDND)

        val ImmersiveModeSwitch = binding.ImmersiveModeSwitch
        ImmersiveModeSwitch.setChecked(viewModel.AppSettings.settingsImmersiveMode)

        val AdvancedSettingsSwitch = binding.AdvancedStatsSwitch
        AdvancedSettingsSwitch.setChecked(viewModel.AppSettings.settingsAdvancedSettings)

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_picker)

        val dialogTextView: TextView = dialog.findViewById(R.id.dialogSettedTextView)
        dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.AppSettings.settingsHours, viewModel.AppSettings.settingsMinutes, viewModel.AppSettings.settingseconds)
        val numberPickerSec: NumberPicker = dialog.findViewById(R.id.secPicker)
        numberPickerSec.minValue = 0
        numberPickerSec.maxValue = 59
        numberPickerSec.value = viewModel.AppSettings.settingseconds
        val numberPickerMin: NumberPicker = dialog.findViewById(R.id.minPicker)
        numberPickerMin.minValue = 0;
        numberPickerMin.maxValue = 59;
        numberPickerMin.value = viewModel.AppSettings.settingsMinutes
        val numberPickerOre: NumberPicker = dialog.findViewById(R.id.orePicker)
        numberPickerOre.minValue = 0;
        numberPickerOre.maxValue = 23;
        numberPickerOre.value = viewModel.AppSettings.settingsHours

        numberPickerOre.setOnValueChangedListener{ numberPicker, i, i2 ->
            viewModel.AppSettings.settingsHours = numberPicker.value
            //viewModel.settingsMinutes = numberPickerMin.value
            //viewModel.settingseconds = numberPickerSec.value
            dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.AppSettings.settingsHours, viewModel.AppSettings.settingsMinutes, viewModel.AppSettings.settingseconds)
            //dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.settingsHours, viewModel.settingsMinutes, viewModel.settingseconds)
        }

        numberPickerMin.setOnValueChangedListener{ numberPicker, i, i2 ->
            //viewModel.settingsHours = numberPickerOre.value
            viewModel.AppSettings.settingsMinutes = numberPicker.value
            //viewModel.settingseconds = numberPickerSec.value
            dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.AppSettings.settingsHours, viewModel.AppSettings.settingsMinutes, viewModel.AppSettings.settingseconds)
            //dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.settingsHours, viewModel.settingsMinutes, viewModel.settingseconds)
        }

        numberPickerSec.setOnValueChangedListener{ numberPicker, i, i2 ->
            //viewModel.settingsHours = numberPickerOre.value
            //viewModel.settingsMinutes = numberPickerMin.value
            viewModel.AppSettings.settingseconds = numberPicker.value
            dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.AppSettings.settingsHours, viewModel.AppSettings.settingsMinutes, viewModel.AppSettings.settingseconds)
            //dialogTextView.text = String.format ("%02d:%02d:%02d", viewModel.settingsHours, viewModel.settingsMinutes, viewModel.settingseconds)
        }

        val dialogOKBtn: Button = dialog.findViewById(R.id.okBtn)
        dialogOKBtn.setOnClickListener{
            //viewModel.saveSettings(requireContext(), viewModel.settingsHours, viewModel.settingsMinutes, viewModel.settingseconds)
            viewModel.saveSettings(requireContext())
            dialog.dismiss()
        }
        val dialogANNULLABtn: Button = dialog.findViewById(R.id.annullaBtn)
        dialogANNULLABtn.setOnClickListener{
            dialog.dismiss()
        }

        binding.settingsTimerView.setOnClickListener {
            dialog.show()
        }

        DNDSwitch.setOnClickListener {
            viewModel.AppSettings.settingsDND = DNDSwitch.isChecked
            viewModel.saveSettings(requireContext())
        }

        ImmersiveModeSwitch.setOnClickListener {
            viewModel.AppSettings.settingsImmersiveMode = ImmersiveModeSwitch.isChecked
            viewModel.saveSettings(requireContext())
        }

        AdvancedSettingsSwitch.setOnClickListener {
            viewModel.AppSettings.settingsAdvancedSettings = AdvancedSettingsSwitch.isChecked
            viewModel.saveSettings(requireContext())
        }

        firebaseAuth = FirebaseAuth.getInstance()

        binding.logoutButton.setOnClickListener {
            firebaseAuth.signOut()

            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
        }


        binding.settingsViewModel = viewModel
        binding.lifecycleOwner= this

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}