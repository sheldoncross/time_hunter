package com.example.timehunter

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (val name:String, val photo :Int = R.drawable.male_placeholder): Parcelable

class UserListAdapter (val group: Group, private val contacts :ArrayList<User>? = null ):RecyclerView.Adapter<UserListAdapter.UserHolder>() {

    class UserHolder(v: View) : RecyclerView.ViewHolder(v) {
        val photoView: CircleImageView
        val checkBox : CheckBox
        val nameView: TextView

        init {
            photoView = v.findViewById(R.id.photo)
            nameView = v.findViewById(R.id.name)
            checkBox = v.findViewById(R.id.contact_check)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserHolder(layout)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {



        var user:User
        if (contacts==null){
            user = group.people[position]

        } else{
            user = contacts[position]
            if (user in group.people){
                holder.checkBox.setChecked(true)
            }
        }

        holder.checkBox.setOnCheckedChangeListener { _, b ->
            if(b){
                group.people.add(user)
            }else{
                group.people.remove(user)
        }
    }

    holder.nameView.text = user.name
    holder.photoView.setImageResource(user.photo)
    }

    override fun getItemCount():Int {
        when(contacts){
            null->return group.people.size
            else->return contacts.size
        }
    }



}
