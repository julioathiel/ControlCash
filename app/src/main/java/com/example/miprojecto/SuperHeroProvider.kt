package com.example.miprojecto

class SuperHeroProvider {
    companion object {
        val superHeroList = listOf<SuperHero>(
            SuperHero("Supermercado", "Chino", R.drawable.ic_banco, "$1500", "15-12-2022"),
            SuperHero("agregado", "Chino", R.drawable.ic_banco, "$1500", "15-12-2022"),
            SuperHero("Supermercado", "Chino", R.drawable.ic_image, "$1500", "15-12-2022"),
            SuperHero("Supermercado", "Chino", R.drawable.ic_share, "$1500","15-12-2022"),
        )
    }
}