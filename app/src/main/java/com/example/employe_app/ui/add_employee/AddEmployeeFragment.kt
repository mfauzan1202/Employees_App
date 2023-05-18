package com.example.employe_app.ui.add_employee

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.employe_app.viewmodel.MainViewModel
import com.example.employe_app.R
import com.example.employe_app.databinding.FragmentAddEmployeeBinding
import com.example.employe_app.db.Employees
import com.example.employe_app.utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddEmployeeFragment : Fragment(R.layout.fragment_add_employee) {

    private var _binding: FragmentAddEmployeeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private val MONTHS = arrayOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember"
    )

    private var selected: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEmployeeBinding.bind(view)

        setUpSpinner()

        binding.apply {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            btnSave.setOnClickListener {
                if (validateInput()) {
                    insertEmployee(selected.toString())
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        binding.apply {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val birthDate = etBirthDate.text.toString()
            val address = etAddress.text.toString()
            val phoneNumber = etPhoneNumber.text.toString()

            if (name.isEmpty()) {
                etName.error = "Nama harus diisi"
            }

            if (email.isEmpty()) {
                etEmail.error = "Email harus diisi"
            } else if (!isValidEmail(email)) {
                etEmail.error = "Email tidak valid"
            }

            if (birthDate.isEmpty()) {
                etBirthDate.error = "Tanggal lahir harus diisi"
            }

            if (address.isEmpty()) {
                etAddress.error = "Alamat harus diisi"
            }

            if (phoneNumber.isEmpty()) {
                etPhoneNumber.error = "Nomor telepon harus diisi"
            }

            if (name.isNotEmpty() &&
                email.isNotEmpty() && isValidEmail(email) &&
                birthDate.isNotEmpty() &&
                address.isNotEmpty() &&
                phoneNumber.isNotEmpty()
            ) {
                return true
            }
        }
        return false
    }

    private fun insertEmployee(gender: String) {
        if (gender == "Pilih Jenis Kelamin") {
            Toast.makeText(
                requireContext(),
                "Pilih Jenis Kelamin Terlebih dahulu",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val dataEmployee = Employees(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                phoneNumber = binding.etPhoneNumber.text.toString(),
                birthDate = binding.etBirthDate.text.toString(),
                address = binding.etAddress.text.toString(),
                gender = gender
            )
            viewModel.insertEmployee(dataEmployee)
        }
    }

    private fun setUpSpinner() {
        val genderArray = resources.getStringArray(R.array.gender_array);
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_spinner_gender,
            R.id.tv_gender,
            genderArray
        )
        binding.apply {
            spGender.adapter = adapter

            etBirthDate.setOnClickListener {
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    R.style.DatePickerDialogTheme,
                    { _, year, month, dayOfMonth ->
                        val monthName = MONTHS[month]
                        etBirthDate.setText("$dayOfMonth ${monthName} $year")
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }

            spGender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selected = parent?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    Toast.makeText(
                        requireContext(),
                        "Pilih Jenis Kelamin",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}