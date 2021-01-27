package com.example.countries.app.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.app.service.Item
import com.example.countries.app.ui.activities.DetailActivity
import com.squareup.picasso.Picasso

class CountriesAdapter(private val items: List<Item>): RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {



//startRegion
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindCountry(items[position])
    }
//endRegion

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView: TextView
        val thumbnailImageView: ImageView

        init {
            titleTextView = itemView.findViewById(R.id.titleTextView)
            thumbnailImageView = itemView.findViewById(R.id.thumbnailmageView)

        }

        fun bindCountry(item: Item) {
            titleTextView.text = item.title

            Picasso.get()
                .load(item.secure_thumbnail)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(thumbnailImageView)

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}