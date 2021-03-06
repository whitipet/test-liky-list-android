package com.whitipet.test.likylist.screen.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.whitipet.test.likylist.R
import com.whitipet.test.likylist.data.entity.Medicine

class MedicinesListAdapter(private val onClick: (Int, View) -> Unit) :
	ListAdapter<Medicine, MedicinesListAdapter.ViewHolder>(FlowerDiffCallback) {

	class ViewHolder(itemView: View, val onClick: (Int, View) -> Unit) : RecyclerView.ViewHolder(itemView) {

		private val viewPlaceholder: View = itemView.findViewById(R.id.view_item_medicine_placeholder)
		private val tvTradeLabel: TextView = itemView.findViewById(R.id.tv_item_medicine_trade_label)
		private val tvManufacturerName: TextView = itemView.findViewById(R.id.tv_item_medicine_manufacturer_name)

		private var currentItem: Medicine? = null

		init {
			itemView.setOnClickListener {
				currentItem?.let {
					onClick(it.id, itemView)
				}
			}
		}

		fun bind(medicine: Medicine) {
			currentItem = medicine

			viewPlaceholder.setBackgroundColor(medicine.color)
			tvTradeLabel.text = medicine.tradeLabel
			tvManufacturerName.text = medicine.manufacturerName
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_medicine, parent, false), onClick)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(getItem(position))
	}

	object FlowerDiffCallback : DiffUtil.ItemCallback<Medicine>() {
		override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
			return oldItem == newItem
		}

		override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
			return oldItem.id == newItem.id
		}
	}
}