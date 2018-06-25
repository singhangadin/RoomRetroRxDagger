package com.github.angads25.roomretrorxdagger.adapters

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.angads25.roomretrorxdagger.R
import com.github.angads25.roomretrorxdagger.retrofit.model.PropertyListing
import kotlinx.android.synthetic.main.list_item_property.view.*

class PropertyListAdapter (
        private val context: Context,
        private val propertyList: List<PropertyListing>
) : RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertyList.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.propertyName.text = propertyList[position].name
        holder.propertyLandmark.text = propertyList[position].landmark
    }

    class PropertyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val propertyName : AppCompatTextView = itemView.property_name
        val propertyLandmark : AppCompatTextView = itemView.property_landmark
    }
}