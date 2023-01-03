package com.sm.sharemobilityapp.data

import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.model.Car

class Datasource {
    fun loadCars(): List<Car> {
        return listOf<Car>(
           // Car(R.drawable.car1, "Audi", "A4", 105.55, 527.75, "23-12-2022", "05-01-2023"),
           // Car(R.drawable.car1, "Audi", "A5", 75.00, 678.25, "05-06-2022", "08-06-2022"),
           // Car(R.drawable.car1, "Audi", "A6", 80.99, 300.69, "12-01-2021", "29-01-2021"),
        )
    }
}