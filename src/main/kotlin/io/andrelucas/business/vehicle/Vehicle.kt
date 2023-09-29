package io.andrelucas.business.vehicle

import io.andrelucas.business.Price
import java.util.*

data class Vehicle(val id: UUID,
                   val vehicleType: VehicleType,
                   val company: String = "",
                   val servicesOffering: List<String>,
                   val estimatePrice: Price){
    companion object {
        fun create(vehicleType: VehicleType,
                   company: String,
                   servicesOffering: List<String>,
                   estimatePrice: Price,
                   adults: Int): Vehicle {
            
            if (vehicleType.ticketLess == TicketLess.YES) {
                return Vehicle(id = UUID.randomUUID(), vehicleType, company, servicesOffering, estimatePrice)
            }
            
            val totalEstimatePrice = estimatePrice.copy(valueInCents = estimatePrice.valueInDollarInCents().times(adults))
            return Vehicle(id = UUID.randomUUID(), vehicleType, company, servicesOffering, totalEstimatePrice)
        }
    }
}

enum class VehicleType(val ticketLess: TicketLess) {
    CAR(TicketLess.YES),
    MOTORCYCLE(TicketLess.YES),
    BUS(TicketLess.NO),
    TRAIN(TicketLess.NO),
    SUBWAY(TicketLess.NO),
    AIRPLANE(TicketLess.NO),
    BOAT(TicketLess.NO),
    OTHER(TicketLess.YES),
    TRUCK(TicketLess.YES),
    RENTAL_VEHICLE(TicketLess.YES)
}

enum class TicketLess{
    YES, NO
}