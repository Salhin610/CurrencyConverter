package com.task.currencyconverter.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.currencyconverter.R
import com.task.currencyconverter.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val viewModel : DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding: FragmentDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val root: View = binding.root

        viewModel.currency1.postValue(arguments?.getString("selectedCurrency"))
        viewModel.currency2.postValue(arguments?.getString("secondCurrency"))
        viewModel.rateOnDay1.postValue(arguments?.getString("todayRate"))
        viewModel.commonRates.postValue(arguments?.getDoubleArray("listOfRatesAgainstCommon"))
        Log.e("Test", arguments?.getDoubleArray("listOfRatesAgainstCommon")?.get(0).toString())

        binding
            .commonCurrenciesRates
            .adapter =
                      CommonCurrenciesAdapter(
            resources.getStringArray(R.array.common_currency_array),
            arguments?.getDoubleArray("listOfRatesAgainstCommon")!!
        )

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
        return root
    }

}