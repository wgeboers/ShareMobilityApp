package com.sm.sharemobilityapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.fragment_your_cars
import com.sm.sharemobilityapp.model.Car

class YoureCarListItemAdapter(
    private val context: fragment_your_cars,
    private val dataset: List<Car>
) : RecyclerView.Adapter<YoureCarListItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
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
        val item = dataset[position]
        holder.imageView.setImageResource(item.image)
        holder.brandModelTextView.text = item.make+" "+item.model
        holder.pricePerDayTextView.text = item.pricePerDay.toString()
    }

    override fun getItemCount() = dataset.size
}