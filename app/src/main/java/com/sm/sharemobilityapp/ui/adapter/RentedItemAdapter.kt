package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.ProfileFragment
import com.sm.sharemobilityapp.model.Car

class RentedItemAdapter(
    private val context: ProfileFragment,
    private val dataset: List<Car>
) : RecyclerView.Adapter<RentedItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_slider)
        val startDateTextView: TextView = view.findViewById(R.id.rented_start_date_text)
        val endDateTextView: TextView = view.findViewById(R.id.rented_end_date_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.rented_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageResource(item.image)
        holder.startDateTextView.text = item.rentedStartDate
        holder.endDateTextView.text = item.rentedEndDate
    }

    override fun getItemCount() = dataset.size
}