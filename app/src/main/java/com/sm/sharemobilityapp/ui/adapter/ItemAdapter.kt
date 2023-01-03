package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.ui.StartFragment
import com.sm.sharemobilityapp.model.Car

class ItemAdapter(
    private val context: StartFragment,
    private val dataset: List<Car>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_slider)
        val brandTextView: TextView = view.findViewById(R.id.brand_text)
        val pricePerDayTextView: TextView = view.findViewById(R.id.price_per_day_text)
        val totalTextView: TextView = view.findViewById(R.id.total_price_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.offer_list_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageResource(item.image)
        holder.brandTextView.text = item.make
        holder.pricePerDayTextView.text = item.pricePerDay.toString()
        holder.totalTextView.text = item.totalPrice.toString()

        holder.imageView.setOnClickListener {
                view -> view.findNavController().navigate(R.id.fragment_car_rental_details)
        }

        //holder.textView.text = context.resources.getString(item.stringResourceId)
    }

    override fun getItemCount() = dataset.size

}