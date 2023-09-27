package io.andrelucas.app

data class AccommodationRequest(val name: String,
                                val type: String,
                                val latitude: Double,
                                val longitude: Double,
                                val servicesOffering: List<String>)