package com.example.timehunter

import android.content.ClipData
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Notification(val title:String, val icon: Int)

class NotificationAdapter (private val notifications:ArrayList<Notification>):
    RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    class NotificationHolder(v: View) : RecyclerView.ViewHolder(v){
        val textView: TextView
        val imageView: ImageView
        val removeView: ImageView

        init{
            textView = v.findViewById(R.id.title)
            imageView = v.findViewById(R.id.icon)
            removeView = v.findViewById(R.id.X)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NotificationHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.notification, parent,false)

        return NotificationHolder(layout)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val item = notifications[position]
        holder.imageView.setImageResource(item.icon)
        holder.textView.text = item.title

        holder.removeView.setOnClickListener {
            NotificationsData.notifications.remove(item)
            notifyItemRemoved(position)
        }

    }

    override fun getItemCount() = notifications.size
}