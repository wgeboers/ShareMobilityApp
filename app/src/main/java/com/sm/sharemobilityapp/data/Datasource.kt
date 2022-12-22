package com.sm.sharemobilityapp.data

import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.model.Car

class Datasource {
    fun loadCars(): List<Car> {
        return listOf<Car>(
            Car(R.drawable.car1, "Audi", "A4", 105.55, 527.75),
        )
    }
}