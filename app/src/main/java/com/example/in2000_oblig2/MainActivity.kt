package com.example.in2000_oblig2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.in2000_oblig2.databinding.ActivityMainBinding
import kotlinx.coroutines.DelicateCoroutinesApi


class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<PartyAdapter.ViewHolder>? = null


    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner = findViewById<Spinner>(R.id.spinne)
        val valg = resources.getStringArray(R.array.distriktValg)

        //henter viewmodel for hver distrikt
        viewModel.fetchxmlParti()
        viewModel.fetchdis1Parti()
        viewModel.fetchdis2Parti()


        ArrayAdapter.createFromResource(
            this,
            R.array.distriktValg,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (valg[position]) {
                    "Dirstrikt 1" -> {

                        viewModel.getdis1Parti().observe(this@MainActivity) {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            val rec = findViewById<RecyclerView>(R.id.recyclerView)

                            rec.layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = PartyAdapter(it)
                            rec.adapter = adapter

                        }
                    }
                    "Dirstrikt 2" -> {
                        viewModel.getdis2Parti().observe(this@MainActivity) {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            val rec = findViewById<RecyclerView>(R.id.recyclerView)

                            rec.layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = PartyAdapter(it)
                            rec.adapter = adapter

                        }
                    }
                    "Dirstrikt 3" -> {

                        viewModel.getxmlParti().observe(this@MainActivity) {
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            val rec = findViewById<RecyclerView>(R.id.recyclerView)

                            rec.layoutManager = LinearLayoutManager(this@MainActivity)
                            adapter = PartyAdapter(it)
                            rec.adapter = adapter

                        }
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(applicationContext, "Select a distrikt", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

//Jeg glemmer ofte hvordan aa skrive Toast msg
//Toast.makeText(applicationContext,"this is toast message ffs",Toast.LENGTH_SHORT).show()
