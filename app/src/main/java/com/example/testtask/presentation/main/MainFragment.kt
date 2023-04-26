package com.example.testtask.presentation.mainpackage

import com.example.testtask.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtask.databinding.FragmentMainBinding
import com.example.testtask.presentation.MainActivity
import com.example.testtask.presentation.main.NumbersAdapter
import com.example.testtask.utils.Resource

class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewModel by lazy {
        (activity as MainActivity).mainViewModel
    }
    private val rvHistory by lazy {
        binding.rvHistory
    }
    private val numbersAdapter = NumbersAdapter()

    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding = FragmentMainBinding.bind(view)

        setupRV()
        viewModel.getAllNumbersDataLiveData().observe(viewLifecycleOwner) {
            numbersAdapter.submitList(it)
        }

        binding.btnGetFact.setOnClickListener {
            viewModel.getFactByNumber(binding.etNumber.getText())
                .observe(viewLifecycleOwner) { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideLoading()
                            binding.tvResult.text = response.data.text
                            viewModel.saveNumberData(response.data)
                        }
                        is Resource.Error -> {
                            hideLoading()
                            response.message.let { message ->
                                Toast.makeText(
                                    activity,
                                    "An error occurred: $message",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                        is Resource.Loading -> showLoading()
                    }
                }
        }

        binding.btnGetRandomFact.setOnClickListener {
            viewModel.getRandomFact().observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        hideLoading()
                        binding.tvResult.text = response.data.text
                        viewModel.saveNumberData(response.data)
                    }
                    is Resource.Error -> {
                        hideLoading()
                        response.message.let { message ->
                            Toast.makeText(
                                activity,
                                "An error occurred: $message",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                    is Resource.Loading -> showLoading()
                }
            }
        }
    }

    private fun showLoading() {
        binding.btnGetFact.isEnabled = false
        binding.btnGetRandomFact.isEnabled = false
        binding.pbLoading.isVisible = true
    }

    private fun hideLoading() {
        binding.btnGetFact.isEnabled = true
        binding.btnGetRandomFact.isEnabled = true
        binding.pbLoading.isVisible = false
    }

    fun setupRV() {
        rvHistory?.apply {
            adapter = numbersAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showError(message: String?) {
        hideLoading()
        Toast.makeText(activity, "An error occurred: $message", Toast.LENGTH_LONG).show()
    }
}