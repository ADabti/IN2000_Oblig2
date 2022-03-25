package com.example.in2000_oblig2

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import de.hdodenhof.circleimageview.CircleImageView


class PartyAdapter(private val dataSet: MutableList<AlpacaParty>) :
    RecyclerView.Adapter<PartyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val navn: TextView = itemView.findViewById(R.id.viewNavn)
        val farge: View = itemView.findViewById(R.id.viewFarge)
        val stemmer: TextView = itemView.findViewById(R.id.viewStemmer)
        var leader: TextView = itemView.findViewById(R.id.viewLeader)
        val bilde: CircleImageView = itemView.findViewById(R.id.viewBilde)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.elements, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    //binder alpacaParty objekter til cardview-et
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.leader.text = "Leader: " + dataSet[position].leader
        viewHolder.navn.text = dataSet[position].name
        viewHolder.farge.setBackgroundColor(Color.parseColor(dataSet[position].color))
        viewHolder.stemmer.text = dataSet[position].stemmetekst.toString()

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(viewHolder.bilde.context).applyDefaultRequestOptions(requestOptions)
            .load(dataSet[position].img)
            .into(viewHolder.bilde)
    }

    override fun getItemCount(): Int {
        return 4 //siden det er 4 partier...
    }
}