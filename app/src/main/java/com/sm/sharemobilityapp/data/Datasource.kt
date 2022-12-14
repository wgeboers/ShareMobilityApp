package com.sm.sharemobilityapp.data

import com.sm.sharemobilityapp.R
import com.sm.sharemobilityapp.model.Car

class Datasource {
    fun loadCars(): List<Car> {
        return listOf<Car>(
            Car(0, R.drawable.car1, "Audi", "A4", 105.55, 527.75, "23-12-2022", "05-01-2023", 52.19611034699973, 5.429496536943859),
            Car(1, R.drawable.car1, "BWM", "A5", 75.00, 678.25, "05-06-2022", "08-06-2022", 52.18955108163518, 5.434581764450099),
            Car(2, R.drawable.car1, "Volvo", "A6", 80.99, 300.69, "12-01-2021", "29-01-2021",52.19803681201673, 5.435323360128094),
            Car(3, R.drawable.car1, "Kenworth", "T370", 80.99, 300.69, "12-01-2021", "29-01-2021",52.19009036631612, 5.432520086455072),
            Car(4, R.drawable.car1, "Aprilia", "ETV 1000", 80.99, 300.69, "12-01-2021", "29-01-2021",52.19782453395682, 5.432176763705607),
            Car(5, R.drawable.car1, "Dodge", "DURANGO", 80.99, 300.69, "12-01-2021", "29-01-2021",52.20124397495712, 5.41432398073341),
            Car(6, R.drawable.car1, "Kawasaki", "VN900B", 80.99, 300.69, "12-01-2021", "29-01-2021",52.18519662650825, 5.431575948894042),
            Car(7, R.drawable.car1, "Gmc", "SIERRA", 80.99, 300.69, "12-01-2021", "29-01-2021",52.20066531882009, 5.40891664742933),
            Car(8, R.drawable.car1, "Ford", "F-550", 80.99, 300.69, "12-01-2021", "29-01-2021",52.19693017521649, 5.434580022951864),
        )
    }
}