package io.andrelucas.business.vehicle

import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class VehicleTest {
    @Test
    fun shouldNotSumEstimatePriceWhenVehicleIsTicketLess() {
        val vehicle = Vehicle.create(
            vehicleType = VehicleType.CAR,
            company = "Company",
            servicesOffering = listOf("Service 1", "Service 2"),
            estimatePrice = Price(10000, Currency.USD),
            adults = 2
        )

        assertEquals(10000, vehicle.estimatePrice.valueInDollarInCents())
    }

    @Test
    fun shouldSumEstimatePriceWhenVehicleIsNotTicketLess() {
        val vehicle = Vehicle.create(
            vehicleType = VehicleType.AIRPLANE,
            company = "Company",
            servicesOffering = listOf("Service 1", "Service 2"),
            estimatePrice = Price(10000, Currency.USD),
            adults = 2
        )

        assertEquals(20000, vehicle.estimatePrice.valueInDollarInCents())
    }
}