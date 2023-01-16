package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.YoureCarListItemBinding
import com.sm.sharemobilityapp.model.CarModel

class YourCarListItemAdapter() : RecyclerView.Adapter<YourCarListItemAdapter.CarViewHolder>() {

    var cars: List<CarModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YourCarListItemAdapter.CarViewHolder {
        val withDataBinding: YoureCarListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            YourCarListItemAdapter.CarViewHolder.LAYOUT,
            parent,
            false
        )
        return (YourCarListItemAdapter.CarViewHolder(withDataBinding))
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: YourCarListItemAdapter.CarViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.carInfo = cars[position]
            it.imageSlider.setOnClickListener {
                val carId = cars[position].id
            }
        }
    }

    class CarViewHolder(val viewDataBinding: YoureCarListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.youre_car_list_item
        }
    }


//    private val carData: List<RegistrationInfo>
//) : RecyclerView.Adapter<CarOwnerListItemAdapter.ItemViewHolder>() {
//
//    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val imageView: ImageView = view.findViewById(R.id.image_slider)
//        val brandModelTextView: TextView = view.findViewById(R.id.brand_text)
//        val pricePerDayTextView: TextView = view.findViewById(R.id.price_per_day_text)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val adapterLayout = LayoutInflater.from(parent.context)
//            .inflate(R.layout.youre_car_list_item, parent, false)
//
//        return ItemViewHolder(adapterLayout)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = carData[position]
//        holder.brandModelTextView.text = item.make + " " + item.model
//        holder.pricePerDayTextView.text = item.hourlyRate.toString()
//    }
//
//    override fun getItemCount() = carData.size
}