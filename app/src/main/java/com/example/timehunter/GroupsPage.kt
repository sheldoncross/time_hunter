package com.example.timehunter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupsPage : Fragment(){


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_groups_page, container, false)
        val groupsView = layout.findViewById<RecyclerView>(R.id.groups)
        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        println(GroupsData.groups)
        groupsView.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = GroupAdapter(GroupsData.groups)
        }
        return layout
    }
}
