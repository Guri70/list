package com.app.listshort

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.listshort.databinding.ItemCityBinding

class ListAdapter constructor(
    private val context: Context,
    private val items: List<ListModel>,
    private val onActionListener: OnActionListener<ListModel>
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ListModel = items.get(position)

        holder.binding.apply {

            if (model.isSelected != null)
                checkboxitem.isChecked = model.isSelected!!

            tvCityNames.text = model.name

            checkboxitem.setOnClickListener {
                onActionListener.notify(model, holder.adapterPosition)
            }

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


    class ViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)

}