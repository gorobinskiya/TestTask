package com.example.testtask.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testtask.R
import com.example.testtask.data.remote.NumberDto
import com.example.testtask.presentation.DETAILS
import com.example.testtask.presentation.NUMBER

class NumbersAdapter() : ListAdapter<NumberDto, NumberItemHolder>(ItemComparator()) {
    class ItemComparator : DiffUtil.ItemCallback<NumberDto>() {
        override fun areItemsTheSame(
            oldItem: NumberDto,
            newItem: NumberDto
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NumberDto,
            newItem: NumberDto
        ): Boolean {
            return oldItem.number == newItem.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return NumberItemHolder(view)
    }

    override fun onBindViewHolder(holder: NumberItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class NumberItemHolder(container: View) : RecyclerView.ViewHolder(container) {
    fun bind(data: NumberDto) {
        itemView.apply {
            this.findViewById<TextView>(R.id.tvNumber).text = data.number.toString()
            this.findViewById<TextView>(R.id.tvDetails).text = data.text
            setOnClickListener {
                val bundle = Bundle()
                bundle.putString(DETAILS, data.text)
                bundle.putString(NUMBER, data.number.toString())
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
            }
        }


    }
}
