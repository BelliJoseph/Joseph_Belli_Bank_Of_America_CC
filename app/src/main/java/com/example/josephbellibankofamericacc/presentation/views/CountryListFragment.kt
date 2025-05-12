package com.example.josephbellibankofamericacc.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.josephbellibankofamericacc.presentation.viewmodel.CountryListViewModel
import com.example.josephbellibankofamericacc.R
import com.example.josephbellibankofamericacc.adapter.CountriesAdapter
import com.example.josephbellibankofamericacc.adapter.CountryClickListener
import com.example.josephbellibankofamericacc.data.domain.CountryListDomainModel
import com.example.josephbellibankofamericacc.databinding.FragmentCountryListBinding
import com.example.josephbellibankofamericacc.data.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryListFragment : BaseFragment(), CountryClickListener {

    val countryListViewModel: CountryListViewModel by activityViewModels()

    private val binding by lazy {
        FragmentCountryListBinding.inflate(layoutInflater)
    }

    private val countryAdapter by lazy {
        CountriesAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.countryRecyclerView.apply {
            val linearLayout =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            layoutManager = linearLayout
            adapter = countryAdapter
        }

        loadData()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            //observe stream only when fragment is active
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryListViewModel.countryList.collect { state ->
                    when (state) {
                        UiState.LOADING -> {
                            binding.countriesProgressBar.visibility = View.VISIBLE
                        }

                        is UiState.SUCCESS -> {
                            binding.countriesProgressBar.visibility = View.GONE
                            val countries: List<CountryListDomainModel> = state.response
                            countryAdapter.setData(countries)
                            countryAdapter.notifyDataSetChanged()
                        }

                        is UiState.ERROR -> {
                            binding.countriesProgressBar.visibility = View.GONE
                            showError(
                                state.error.message ?: R.string.error_default_message.toString()
                            ) {
                                countryListViewModel.getCountriesList()
                            }
                        }
                    }
                }
            }
        }

    }

    override fun onCountryClickListener(item: CountryListDomainModel) {
        val action = CountryListFragmentDirections.actionListFragmentToDetailFragment(
            countryId = item.code
        )
        findNavController().navigate(action)
    }

}