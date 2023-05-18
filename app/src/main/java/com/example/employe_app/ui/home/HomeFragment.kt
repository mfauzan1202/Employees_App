package com.example.employe_app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employe_app.viewmodel.MainViewModel
import com.example.employe_app.R
import com.example.employe_app.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var homeAdapter = HomeAdapter{employee ->
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(employee))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        setupRecyclerView()
        binding.apply {
            fabAddEmployee.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddEmployeeFragment())
            }
        }
        getEmployeeData()
        setupSearchView()
    }

    private fun setupRecyclerView() {

        binding.rvHome.apply {
            adapter = homeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getEmployeeData() {
        viewModel.allEmployee.observe(viewLifecycleOwner ){ employees ->
            if (employees.isNotEmpty()) {
                homeAdapter.submitList(employees)
            } else {
                Log.d("HomeFragment", "No data")
            }
        }

    }

    private fun setupSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchEmployees(query).observe(viewLifecycleOwner){ employees ->
                    if (employees.isNotEmpty()) {
                        homeAdapter.submitList(employees)
                    } else {
                        Log.d("HomeFragment", "No data")
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchEmployees(newText).observe(viewLifecycleOwner){ employees ->
                    if (employees.isNotEmpty()) {
                        homeAdapter.submitList(employees)
                    } else {
                        Log.d("HomeFragment", "No data")
                    }
                }
                return true
            }
        })
    }
}