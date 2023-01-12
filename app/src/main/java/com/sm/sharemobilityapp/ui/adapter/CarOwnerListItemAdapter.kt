package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.CarOwnerFragment
import com.sm.sharemobilityapp.model.Car
import com.sm.sharemobilityapp.network.Registration

class CarOwnerListItemAdapter(
    private val carData: List<Registration>
) : RecyclerView.Adapter<CarOwnerListItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_slider)
        val brandModelTextView: TextView = view.findViewById(R.id.brand_text)
        val pricePerDayTextView: TextView = view.findViewById(R.id.price_per_day_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.youre_car_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = carData[position]
        holder.brandModelTextView.text = item.make+" "+item.model
        holder.pricePerDayTextView.text = item.hourlyRate.toString()
    }

    override fun getItemCount() = carData.size
}