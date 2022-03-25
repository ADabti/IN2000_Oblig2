package com.example.in2000_oblig2

import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import kotlinx.coroutines.*


class DataSource : AppCompatActivity() {

    // alle url krevd i programmet
    private val urlAPi =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v22/obligatoriske-oppgaver/alpacaparties.json"
    private val distrikt1 =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v22/obligatoriske-oppgaver/district1.json"
    private val distrikt2 =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v22/obligatoriske-oppgaver/district2.json"
    private val distriktXML =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v22/obligatoriske-oppgaver/district3.xml"

    private val gson = Gson()

    // skal kun returnere list med alpaca partier
    private suspend fun fetchApi(): MutableList<AlpacaParty>? {
        return try {
            val partier: MutableList<AlpacaParty> = mutableListOf()
            val response = gson.fromJson(Fuel.get(urlAPi).awaitString(), Base::class.java)
            val elements = response.parties
            for (el in elements) {
                partier.add(el)
            }
            partier
        } catch (exception: Exception) {
            null
        }
    }

    // skal kalle på api, telle stemmer og fetch stemmer til distrikt 1
    suspend fun fetchDis1(): MutableList<AlpacaParty>? {
        return try {
            val partier: MutableList<AlpacaParty>? = fetchApi()
            val response = gson.fromJson(Fuel.get(distrikt1).awaitString(), Array<Id>::class.java)

            telleStemmJson(partier!!, response)

            partier
        } catch (exception: Exception) {
            null
        }
    }

    // skal kalle på api, telle stemmer og fetch stemmer til distrikt 2
    suspend fun fetchDis2(): MutableList<AlpacaParty>? {
        return try {
            val partier: MutableList<AlpacaParty>? = fetchApi()
            val response = gson.fromJson(Fuel.get(distrikt2).awaitString(), Array<Id>::class.java)

            telleStemmJson(partier!!, response)

            partier
        } catch (exception: Exception) {
            null
        }
    }

    // skal telle stemmer for distrikt 1 og 2
    private fun telleStemmJson(p: MutableList<AlpacaParty>, a: Array<Id>) {
        try {
            var totalStemmer = 0

            for (i in a) {// get total votes for the district
                totalStemmer++
            }

            var stemmeTall: Int
            for (i in p) {
                stemmeTall = 0
                for (j in a) {
                    if (i.id == j.id) {
                        stemmeTall++
                        i.stemme = stemmeTall
                    }
                }
            }
            for (i in p) {
                var prosent = (i.stemme?.toDouble()?.div(totalStemmer))?.times(100)
                prosent = String.format("%.2f", prosent).toDouble()
                i.stemmetekst = "Votes: ${i.stemme.toString()} - $prosent% "
            }

        } catch (exception: Exception) {
            println("A network request exception was thrown: ${exception.message}")
        }
    }

    // skal kalle på api, telle stemmer og fetch stemmer til distrikt 3 med xml
    @Suppress("BlockingMethodInNonBlockingContext")// I get inappropriate method call in line 104 and this suppress fixes it
    suspend fun fetchXML(): MutableList<AlpacaParty> {

            val xml = Fuel.get(distriktXML).awaitString()
            val inputStream = xml.byteInputStream()
            val xmlpartier: MutableList<AlpacaParty>? = fetchApi()
            val xmlTilList = AlpacaParser().parse(inputStream)
            var totalStemmer = 0

            for (j in xmlTilList) {
                totalStemmer += j.votes!!
            }

            for (i in xmlpartier!!) {
                for (j in xmlTilList) {
                    if (i.id == j.id) {
                        i.stemme = j.votes

                        var prosent = (i.stemme?.toDouble()?.div(totalStemmer))?.times(100)
                        prosent = String.format("%.2f", prosent).toDouble()
                        i.stemmetekst = "Votes: ${i.stemme.toString()} - $prosent% "
                    }
                }
            }
            return xmlpartier
        }
}
