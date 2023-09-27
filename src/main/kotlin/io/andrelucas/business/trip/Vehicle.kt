package io.andrelucas.business.trip

import java.util.*

data class Vehicle(val id: UUID,
                   val vehicleType: VehicleType,
                   val company: String = "",
                   val servicesOffering: List<String>,
                   val tripId: String,
                   val estPrice: Int,) {
}


enum class VehicleType {
    CAR, MOTORCYCLE, BUS, TRAIN, SUBWAY, AIRPLANE, BOAT, OTHER, TRUCK, RENTAL_VEHICLE
}