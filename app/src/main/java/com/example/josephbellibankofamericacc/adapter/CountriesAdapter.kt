package com.example.josephbellibankofamericacc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.josephbellibankofamericacc.R
import com.example.josephbellibankofamericacc.data.domain.CountryListDomainModel

class CountriesAdapter(
    private val clickListener: CountryClickListener,
    private val countryList: MutableList<CountryListDomainModel> = mutableListOf()
) : RecyclerView.Adapter<CountriesViewHolder>() {
    fun setData(countries: List<CountryListDomainModel>) {
        countryList.clear()
        countryList.addAll(countries)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_item_view, parent, false)
        return CountriesViewHolder(
            countryView = view,
            clickListener = clickListener
        )
    }

    override fun onBindViewHolder(
        holder: CountriesViewHolder,
        position: Int
    ) {
        val countryItem = countryList[position]
        holder.bind(countryItem)
    }

    override fun getItemCount(): Int = countryList.size

}

class CountriesViewHolder(
    private val countryView: View,
    private val clickListener: CountryClickListener
) : RecyclerView.ViewHolder(countryView) {

    private val name: TextView = countryView.findViewById(R.id.countryName)
    private val region: TextView = countryView.findViewById(R.id.countryRegion)
    private val code: TextView = countryView.findViewById(R.id.countryCode)
    private val capital: TextView = countryView.findViewById(R.id.countryCapital)


    fun bind(country: CountryListDomainModel) {
        name.text = country.name
        region.text = country.region
        code.text = country.code
        capital.text = country.capital

        countryView.setOnClickListener {
            clickListener.onCountryClickListener(item = country)
        }
    }
}