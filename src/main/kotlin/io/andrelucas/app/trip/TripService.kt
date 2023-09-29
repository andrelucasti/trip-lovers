package io.andrelucas.app.trip

import io.andrelucas.app.accommodation.AccommodationRequest
import io.andrelucas.app.accommodation.AccommodationResponse
import io.andrelucas.app.locality.LocalityRequest
import io.andrelucas.app.locality.LocalityResponse
import io.andrelucas.app.vehicle.VehicleRequest
import io.andrelucas.business.Coordinates
import io.andrelucas.business.trip.*
import io.andrelucas.business.Currency
import io.andrelucas.business.Price
import io.andrelucas.business.accommodation.Accommodation
import io.andrelucas.business.accommodation.AccommodationType
import io.andrelucas.business.locality.Locality
import io.andrelucas.business.locality.LocalityType
import io.andrelucas.business.vehicle.Vehicle
import io.andrelucas.business.vehicle.VehicleType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TripService(private val tripRepository: TripRepository,
                  private val tripDao: TripDao) {
    suspend fun create(tripRequest: TripRequest) =
        Trip.create(tripRequest.title,
            tripRequest.destination,
            tripRequest.about,
            LocalDate.parse(tripRequest.departure),
            LocalDate.parse(tripRequest.returns),
            createLocalities(tripRequest.localities),
            createAccommodations(tripRequest.accommodations),
            createVehicles(tripRequest.vehicles, tripRequest.adults),
            tripRequest.needsVisa,
            tripRequest.adults,
            UUID.fromString(tripRequest.userId))
            .let {
                tripRepository.save(it)
            }

    private fun createVehicles(vehicles: List<VehicleRequest>, adults: Int): List<Vehicle> {
        return vehicles.map {
            Vehicle.create(
                VehicleType.valueOf(it.type),
                it.company,
                it.servicesOffering,
                Price(it.estimatePriceInCents, Currency.USD),
                adults
            )
        }
    }

    suspend fun findAll() = tripRepository.findAll()
        .map { trip ->  TripResponse(
            trip.id.toString(),
            trip.title,
            trip.destination,
            trip.about,
            trip.departure.format(DateTimeFormatter.ISO_LOCAL_DATE),
            trip.returns.format(DateTimeFormatter.ISO_LOCAL_DATE),
            trip.localities.map { locality-> LocalityResponse(locality.id.toString(), locality.name, locality.coordinates.latitude, locality.coordinates.longitude, locality.localityType.name, locality.estPrice.valueInDollarInCents()) },
            trip.accommodations.map { accommodation ->  AccommodationResponse(accommodation.id.toString(), accommodation.name, accommodation.coordinates.latitude, accommodation.coordinates.longitude, accommodation.accommodationType.name, accommodation.estPrice.valueInDollarInCents(), accommodation.servicesOffering) },
            trip.needsVisa,
            trip.estTotalPrice.valueInDollarInCents(),
            trip.adults,
            trip.userId.toString())
        }

    suspend fun findAllFutureTrips(currentDate: LocalDate, userId: UUID) =
        tripDao.findAllFutureTrips(currentDate, userId).sortedByDescending { it.departure }

    private fun createLocalities(localities: List<LocalityRequest>) =
        localities.map {
            Locality.create(
                it.name,
                LocalityType.valueOf(it.type),
                Coordinates(it.latitude, it.longitude),
                "",
                Price(it.estimatePriceInCents, Currency.USD)
            )
        }

    private fun createAccommodations(accommodations: List<AccommodationRequest>) =
        accommodations.map {
            Accommodation.create(
                it.name,
                AccommodationType.valueOf(it.type),
                Coordinates(it.latitude, it.longitude),
                "",
                Price(it.estimatePriceInCents, Currency.USD),
                it.servicesOffering
            )
        }
}
