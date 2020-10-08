package com.example.timehunter

import android.graphics.Color
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.parcel.Parcelize

data class GroupEventItem(val title: String, val description:String, val iconId: Int)

// We should have an Event class but due to time group is fine
@Parcelize
data class Group (var name: String,
                  var summary: String,
                  var icon :Int = R.drawable.ic_group_black_24dp,
                  var people: ArrayList<User> = ArrayList<User>(),
                  var events: ArrayList<Group> = ArrayList<Group>()) :Parcelable

class GroupAdapter (private val groups:ArrayList<Group>):
    RecyclerView.Adapter<GroupAdapter.GroupHolder>() {

    class GroupHolder(v: View) : RecyclerView.ViewHolder(v) {
        val nameView: TextView
        val imageView: ImageView
        val summaryView : TextView
        val sizeView : TextView
        val cardView: CardView

        init {
            nameView = v.findViewById(R.id.name)
            summaryView = v.findViewById(R.id.summary)
            sizeView = v.findViewById(R.id.size)
            imageView = v.findViewById(R.id.photo)
            cardView = v.findViewById(R.id.group_item)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)
        return GroupHolder(layout)
    }


    override fun onBindViewHolder(holder: GroupHolder, position: Int) {
        val group = groups[position]
        holder.imageView.setImageResource(group.icon)
        holder.nameView.text = group.name
        holder.summaryView.text = group.summary
        val sizeString = "${group.people.size} members"
        holder.sizeView.text =   sizeString

        if (position%2==0){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.cardView.context,R.color.colorPrimaryLight))
            holder.nameView.setTextColor(ContextCompat.getColor(holder.cardView.context,R.color.colorPrimaryDark))
            holder.summaryView.setTextColor(ContextCompat.getColor(holder.cardView.context,R.color.colorPrimaryDark))
            holder.sizeView.setTextColor(ContextCompat.getColor(holder.cardView.context,R.color.colorPrimaryDark))
        }

        val a = ViewGroupFragment.newInstance(group).arguments
        holder.cardView.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.viewGroupFragment,a))
    }

    override fun getItemCount() = groups.size

}

