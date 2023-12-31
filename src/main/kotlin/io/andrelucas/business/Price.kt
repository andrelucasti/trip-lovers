package io.andrelucas.business

data class Price(private val valueInCents: Int, private val currency: Currency) {

    fun valueInDollarInCents(): Int{
        return when(currency){
            Currency.BRL -> valueInCents.div(5)
            Currency.EUR -> valueInCents.times(1.2).toInt()
            Currency.GBP -> valueInCents.times(1.4).toInt()
            else -> valueInCents
        }
    }
}

