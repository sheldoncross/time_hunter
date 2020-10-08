package com.example.timehunter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.media.Image
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.fragment_contact_page.*
import kotlinx.android.synthetic.main.fragment_create_group.*

class ContactsPage : Fragment() {

    val args: ContactsPageArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_contact_page, container, false)
        val contactsView = layout.findViewById<RecyclerView>(R.id.contact_recycler)
        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val doneButton = layout.findViewById<MaterialButton>(R.id.done_button)

        val navController = findNavController()
        val group = args.group

        contactsView.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = UserListAdapter(group,ContactsData.contacts)
        }


        doneButton.setOnClickListener{
            navController.popBackStack()
        }



        return layout
    }
}