package com.sm.sharemobilityapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.databinding.RentedListItemBinding
import com.sm.sharemobilityapp.model.ReservationModel
import com.sm.sharemobilityapp.utils.DateConverterUtils

class RentedItemAdapter(): RecyclerView.Adapter<RentedItemAdapter.ReservationViewHolder>() {

    var reservations: List<ReservationModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentedItemAdapter.ReservationViewHolder {
        val withDataBinding: RentedListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            RentedItemAdapter.ReservationViewHolder.LAYOUT,
            parent,
            false
        )
        return (RentedItemAdapter.ReservationViewHolder(withDataBinding))
    }

    override fun getItemCount() = reservations.size

    override fun onBindViewHolder(holder: RentedItemAdapter.ReservationViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.reservationInfo = reservations[position]
            it.imageSlider.setOnClickListener {
                val reservationId = reservations[position].id
            }
        }
    }

    class ReservationViewHolder(val viewDataBinding: RentedListItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.rented_list_item
        }
    }
}