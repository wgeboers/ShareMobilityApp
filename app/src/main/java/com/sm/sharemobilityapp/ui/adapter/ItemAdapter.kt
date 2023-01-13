package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.data.car.Car
import com.sm.sharemobilityapp.databinding.OfferListItemBinding
import com.sm.sharemobilityapp.model.CarModel
import com.sm.sharemobilityapp.ui.CarRentalDetailsFragmentDirections
import com.sm.sharemobilityapp.ui.StartFragmentDirections
import com.sm.sharemobilityapp.ui.viewmodel.CarViewModel

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.CarViewHolder>() {

    var cars : List<CarModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val withDataBinding: OfferListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            CarViewHolder.LAYOUT,
            parent,
            false
        )
        return (CarViewHolder(withDataBinding))
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.carInfo = cars[position]
            it.imageSlider.setOnClickListener {
                val carId = cars[position].id
                val directions = StartFragmentDirections.actionFragmentStartToFragmentCarRentalDetails(carId)
                it.findNavController().navigate(directions)
            }
        }
    }

    class CarViewHolder(val viewDataBinding: OfferListItemBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
                companion object{
                    @LayoutRes
                    val LAYOUT = R.layout.offer_list_item
                }
            }

}
