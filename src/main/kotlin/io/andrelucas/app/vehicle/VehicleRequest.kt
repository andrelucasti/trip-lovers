package io.andrelucas.app.vehicle

import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.vehicle.Vehicle
import io.andrelucas.business.vehicle.VehicleType
import kotlinx.serialization.Serializable

@Serializable
data class VehicleRequest(val company: String,
                          val type: String,
                          val estimatePriceInCents: Int,
                          val servicesOffering: List<String>)


fun VehicleRequest.toVehicle(adults: Int) = Vehicle.create(
    company = this.company,
    vehicleType = VehicleType.valueOf(this.type),
    servicesOffering = this.servicesOffering,
    estimatePrice = Price(this.estimatePriceInCents, Currency.USD),
    adults = adults
)