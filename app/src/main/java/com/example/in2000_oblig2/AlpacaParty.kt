package com.example.in2000_oblig2


data class Base(val parties: MutableList<AlpacaParty>)

data class AlpacaParty(val id: String?, val name: String?, val leader: String?, val img: String?, val color: String?, var stemme: Int?, var stemmetekst : String?)

data class Id(val id : String?)