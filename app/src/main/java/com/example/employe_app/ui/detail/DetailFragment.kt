package com.example.employe_app.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.employe_app.viewmodel.MainViewModel
import com.example.employe_app.R
import com.example.employe_app.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        binding.apply {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            tvFieldName.text = args.employee.name
            tvFieldEmail.text = args.employee.email
            tvFieldBirthDate.text = args.employee.birthDate
            tvFieldAddress.text = args.employee.address
            tvFieldPhoneNumber.text = args.employee.phoneNumber
            tvFieldGender.text = args.employee.gender

            btnDelete.setOnClickListener {
                deleteEmployee()
                findNavController().popBackStack()
            }

            btnEdit.setOnClickListener {
                findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToEditFragment(args.employee))
            }
        }
    }

    private fun deleteEmployee(){
        viewModel.deleteEmployee(args.employee)
    }
}