package com.example.josephbellibankofamericacc.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.josephbellibankofamericacc.R
import com.example.josephbellibankofamericacc.data.domain.CountryDetailDomainModel
import com.example.josephbellibankofamericacc.databinding.FragmentCountryDetailsBinding
import com.example.josephbellibankofamericacc.data.util.UiState
import com.example.josephbellibankofamericacc.presentation.viewmodel.CountryDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailsFragment : BaseFragment() {

    val countryDetailViewModel: CountryDetailViewModel by activityViewModels()

    private lateinit var countryCode: String

    private val binding by lazy {
        FragmentCountryDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: CountryDetailsFragmentArgs by navArgs()
        countryCode = args.countryId

        countryDetailViewModel.getCountryDetails(countryCode = countryCode)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadData()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            //observe stream only when fragment is active
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                countryDetailViewModel.countryDetails.collect { state ->
                    when (state) {
                        UiState.LOADING -> {
                            binding.detailLayout.visibility = View.GONE
                            binding.countriesProgressBar.visibility = View.VISIBLE
                        }

                        is UiState.SUCCESS -> {
                            populateView(state.response)
                            binding.detailLayout.visibility = View.VISIBLE
                            binding.countriesProgressBar.visibility = View.GONE
                        }

                        is UiState.ERROR -> {
                            binding.detailLayout.visibility = View.GONE
                            binding.countriesProgressBar.visibility = View.GONE
                            showError(
                                error = state.error.message
                                    ?: R.string.error_default_message.toString()
                            ) {
                                countryDetailViewModel.getCountryDetails(countryCode = countryCode)
                            }
                        }
                    }
                }
            }
        }

    }

    private fun populateView(countryDetail: CountryDetailDomainModel) {
        binding.countryName.text = countryDetail.name
        binding.countryRegion.text = countryDetail.region
        binding.countryCode.text = countryDetail.code
        binding.countryCapital.text = countryDetail.capital
        binding.currencyName.text = countryDetail.currencyName
        val currencySymbol: String = (if (countryDetail.currencySymbol.isBlank()) {
            R.string.country_currency_empty_default
        } else {
            countryDetail.currencySymbol
        }).toString()
        binding.currencySymbol.text = currencySymbol
        binding.languageName.text = countryDetail.languageName
    }

}