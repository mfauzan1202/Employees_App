package com.example.employe_app.ui.home

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.employe_app.databinding.ItemEmployeBinding
import com.example.employe_app.db.Employees

class HomeAdapter(val clickListener: ((Employees) -> Unit)) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: ItemEmployeBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: Employees) {
                binding.apply {
                    if (data.gender == "Laki-laki"){
                        tvName.text = "Pak ${data.name}"
                    }else{
                        tvName.text = "Bu ${data.name}"
                    }

                    tvEmail.text = data.email
                }
                itemView.setOnClickListener {
                    clickListener.let { it(data) }
                }
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = ItemEmployeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val employees = differ.currentList[position]
        holder.bind(employees)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Employees>() {
        override fun areItemsTheSame(oldItem: Employees, newItem: Employees): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employees, newItem: Employees): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    fun submitList(list: List<Employees>) = differ.submitList(list)
}