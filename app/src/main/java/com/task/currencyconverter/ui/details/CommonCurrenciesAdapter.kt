package com.task.currencyconverter.ui.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.currencyconverter.databinding.ItemRecyclerCommonCurrenciesListBinding

class CommonCurrenciesAdapter(private val commonCurrenciesList: Array<String>, private val commonCurrenciesRates: DoubleArray): RecyclerView.Adapter<CommonCurrenciesAdapter.ViewHolder>(){

    class ViewHolder(val binding: ItemRecyclerCommonCurrenciesListBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            ItemRecyclerCommonCurrenciesListBinding.inflate( LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.textView.text = "${commonCurrenciesList[position]}: ${String.format("%.3f",  commonCurrenciesRates[position])}"

    }

    override fun getItemCount(): Int {
        Log.e("TestRecyclersize", "${commonCurrenciesRates.size} test")

        return commonCurrenciesRates.size
    }
}