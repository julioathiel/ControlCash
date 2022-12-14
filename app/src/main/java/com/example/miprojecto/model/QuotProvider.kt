package com.example.miprojecto.model

class QuotProvider {

    companion object {
        fun random(): QuotModel {
            val position: Int = (0..6).random()
            return quotLis[position]
        }

        private val quotLis = listOf<QuotModel>(
            QuotModel("Ejemplo", "julio"),
            QuotModel("Ejemplo", "cesar"),
            QuotModel("Ejemplo", "santos"),
            QuotModel("Ejemplo", "athiel"),
            QuotModel("Ejemplo", "ivana"),
            QuotModel("Ejemplo", "rossi"),
            QuotModel("Ejemplo", "prueba"),
        )
    }
}