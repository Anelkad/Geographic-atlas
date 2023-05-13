package com.example.geographicatlas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.geographicatlas.databinding.FragmentCountryDetailsBinding

class CountryDetailsFragment : Fragment() {

    lateinit var binding: FragmentCountryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailsBinding.inflate(inflater, container, false)

        binding.toolBar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        return binding.root
    }

}