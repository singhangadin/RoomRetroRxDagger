package com.github.angads25.roomretrorxdagger.adapters

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.angads25.roomretrorxdagger.R
import com.github.angads25.roomretrorxdagger.architecture.model.PropertyListing
import com.github.angads25.roomretrorxdagger.dagger.qualifier.ActivityContext
import kotlinx.android.synthetic.main.list_item_property.view.*
import javax.inject.Inject

class PropertyListAdapter @Inject constructor (
        @ActivityContext private val context: Context
) : RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder>() {
    private val propertyList: ArrayList<PropertyListing> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_property, parent, false)
        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int { return propertyList.size }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.propertyName.text = propertyList[position].name
        holder.propertyLandmark.text = propertyList[position].landmark
        holder.propertyPrice.text = "₹ ${propertyList[position].price}"
        holder.propertyReviews.text = "${propertyList[position].reviewCount} Reviews"
    }

    fun replace(propertyListing: List<PropertyListing>) {
        clear()
        add(propertyListing)
    }

    private fun clear() {
        val size = itemCount
        propertyList.clear()
        notifyItemRangeRemoved(0, size)
    }

    private fun add (propertyListing: List<PropertyListing>) {
        val listSize = this.propertyList.size
        this.propertyList.addAll(propertyListing)
        notifyItemRangeInserted(listSize, itemCount)
    }

    class PropertyViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        val propertyName : AppCompatTextView = itemView.tv_name
        val propertyPrice : AppCompatTextView = itemView.tv_price
        val propertyReviews : AppCompatTextView = itemView.tv_reviews
        val propertyLandmark : AppCompatTextView = itemView.tv_landmark
    }
}