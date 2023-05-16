package com.example.geographicatlas.fragments

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.geographicatlas.R
import com.example.geographicatlas.adapters.CountryDetailsAdapter
import com.example.geographicatlas.databinding.FragmentCountryDetailsBinding
import com.example.geographicatlas.models.Country
import com.example.geographicatlas.utils.CHANNEL_ID
import com.example.geographicatlas.utils.NOTIFICATION_ID
import com.example.geographicatlas.utils.Resource
import com.example.geographicatlas.viewmodel.CountryViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CountryDetailsFragment : Fragment() {

    lateinit var binding: FragmentCountryDetailsBinding
    val arg: CountryDetailsFragmentArgs by navArgs()
    lateinit var countryDetailsAdapter: CountryDetailsAdapter

    private val countryViewModel by activityViewModels<CountryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        countryViewModel.getCountryDetails(arg.cca2)
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countryViewModel.countryDetailsState.observe(
            viewLifecycleOwner, Observer{
                when(it){
                    is Resource.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        val country = it.getSuccessResult()
                        //Log.d("country detail",country.toString())
                        bindCountry(country)
                        showNotification(country)
                        }
                    else -> Unit
                }
            }
        )

        binding.toolBar.setNavigationOnClickListener {
            countryViewModel.clearCountryDetailsState()
            findNavController().popBackStack()
        }
    }

    private fun bindCountry(country: Country){
        binding.toolBar.title = country.name?.common

        Glide
            .with(this)
            .load(country.flags?.png)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.baseline_image_not_supported_24)
            .into(binding.countryFlagImageView)

        val countryDetailsPair = country.toDetailsMap().toList()

        countryDetailsAdapter = CountryDetailsAdapter(countryDetailsPair){
            val googleMapUri: Uri = Uri.parse(country.maps?.googleMaps ?: "")
            val mapIntent = Intent(Intent.ACTION_VIEW, googleMapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        binding.countryDetailsList.adapter = countryDetailsAdapter

    }

    private fun showNotification(country: Country){
        val notification = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setContentTitle(country.name?.common)
            .setContentText("region: ".plus(country.continents[0]))
            .setSmallIcon(R.drawable.earth)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notificationManager = activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                requireContext(),
                "First enable NOTIFICATION ACCESS in settings.",
                Toast.LENGTH_LONG
            ).show();
            return
        }
        notificationManager.notify(NOTIFICATION_ID,notification)
    }

}