package com.task.currencyconverter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.task.currencyconverter.R
import com.task.currencyconverter.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class HomeFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    val viewModel : HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val root: View = binding.root


        binding.detailsScreen.setOnClickListener {

            val bundle = Bundle().apply {


                this.putString("selectedCurrency", binding.spinnerCurrency1.selectedItem.toString())
                this.putString("secondCurrency", binding.spinnerCurrency2.selectedItem.toString())
                this.putString("todayRate", viewModel.getTodayRateForUnit())
                this.putDoubleArray("listOfRatesAgainstCommon", viewModel.getCommonCurrenciesRates())
            }

            // Navigate to the details fragment
            findNavController().navigate(R.id.navigation_details, bundle)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }








}