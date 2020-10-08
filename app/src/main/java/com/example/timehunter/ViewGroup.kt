package com.example.timehunter


import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter
import com.prolificinteractive.materialcalendarview.spans.DotSpan
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_carousel_calendar.*
import kotlinx.android.synthetic.main.fragment_view_group.view.*
import java.lang.IllegalStateException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

class ViewGroupFragment : Fragment() {




    companion object {
        const val ARG_GROUP = "GROUP"

        fun newInstance(group: Group): ViewGroupFragment {
            val fragment = ViewGroupFragment()
            val bundle = Bundle().apply {
                putParcelable(ARG_GROUP,group)
            }

            fragment.arguments = bundle
            return fragment
        }
    }


    private lateinit var group: Group

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_view_group, container, false)
        val groupPhotoView = layout.findViewById<CircleImageView>(R.id.photo)
        val titleView = layout.findViewById<TextView>(R.id.name)
        val descriptionView = layout.findViewById<TextView>(R.id.description)
        val membersView = layout.findViewById<RecyclerView>(R.id.contacts)
        val eventsView = layout.findViewById<RecyclerView>(R.id.upcomingEvents)
        val contactsListViewer = layout.findViewById<RecyclerView>(R.id.contacts_list)
        val calendarButton = layout.findViewById<MaterialButton>(R.id.calendarButton)
        val weekCalendarView = layout.findViewById<MaterialCalendarView>(R.id.weekCalendarView)
        val openContacts = layout.findViewById<MaterialButton>(R.id.open_contacts)
        val openOptions = layout.findViewById<MaterialButton>(R.id.open_options)
        val contactsDrawer = layout.findViewById<DrawerLayout>(R.id.contacts_drawer)
        val noContactsView = layout.findViewById<TextView>(R.id.no_contacts)
        val noEvents = layout.findViewById<TextView>(R.id.noEvents)
        val addContactButton = layout.findViewById<FloatingActionButton>(R.id.add_contact_floating)

        val contactsNavigationView = layout.findViewById<NavigationView>(R.id.contactsNavigationView)
        val contactsHeaderLayout = contactsNavigationView.getHeaderView(0)
        val addContactDrawerButton = contactsHeaderLayout.findViewById<FloatingActionButton>(R.id.add_contact)
        val navController = findNavController()

        val context = requireContext()
        group = arguments!!.getParcelable<Group>(ARG_GROUP) as Group
        addContactButton.setOnClickListener {
            val action = ViewGroupFragmentDirections.actionViewGroupFragmentToContactsPage2(group)
            navController.navigate(action)
        }
        addContactDrawerButton.setOnClickListener{
            val action = ViewGroupFragmentDirections.actionViewGroupFragmentToContactsPage2(group)
            navController.navigate(action)
        }

        val icon =  group.icon
        val title = group.name
        val description = group.summary
        val users = group.people
        val events = group.events

        groupPhotoView.setImageResource(icon)
        titleView.text = title
        descriptionView.text = description
        weekCalendarView.topbarVisible=false

        val today : CalendarDay= weekCalendarView.currentDate

        val calendarDay = arrayListOf(today)
        weekCalendarView.addDecorator(EventDecorator(context,calendarDay))

        if (users.isEmpty()) {
            noContactsView.visibility=View.VISIBLE
        }else{
            noContactsView.visibility=View.GONE
        }

        if (events.isEmpty()) {
            noEvents.visibility=View.VISIBLE
        }else{
            noEvents.visibility=View.GONE
        }

        // Configure the events and contacts icons and drawer adapters
        configureContacts(membersView,contactsListViewer, users)
        confiureEvents(eventsView, events)

        val fm = childFragmentManager
        calendarButton.setOnClickListener {
            val calendarFragment = CalendarDialog()
            calendarFragment.show(fm,"Calendar")
        }

        openContacts.setOnClickListener {
            if (contactsDrawer.isDrawerOpen(GravityCompat.END)){
                contactsDrawer.closeDrawer(GravityCompat.END)
            } else {
                contactsDrawer.openDrawer(GravityCompat.END)
            }
        }


        val popup = PopupMenu(openOptions.context,openOptions)
        openOptions.setOnClickListener {
            popup.menuInflater.inflate(R.menu.viewgroup_contacts_options, popup.menu)
            popup.show()
        }

        popup.setOnMenuItemClickListener{
                when (it.itemId) {
                    R.id.menu_leave -> {
                        GroupsData.groups.remove(group)
                        findNavController().popBackStack()
                        false
                    }
                    R.id.menu_edit -> {
                        true
                    }
                    else -> super.onOptionsItemSelected(it)
                }
            }
        // This may be time permitting
        //addContactButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.add_contact,FragArgs))



        return layout
    }


class EventDecorator(val context: Context, dates:ArrayList<CalendarDay>): DayViewDecorator {
    private var dates: HashSet<CalendarDay>
    init {
        this.dates = HashSet(dates)
    }

    override fun shouldDecorate(day: CalendarDay) :Boolean{
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        val event = ContextCompat.getDrawable(context,R.drawable.ic_event_black_24dp) as Drawable

    }
}


fun configureContacts(iconsViewer: RecyclerView, contactsList: RecyclerView, contacts: ArrayList<User>) {
        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        iconsViewer.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = ContactsAdapter(contacts)
        }

        val contactsListManger = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        contactsList.apply {
            setHasFixedSize(true)
            layoutManager = contactsListManger
            adapter = UserListAdapter(group)
        }
    }

    fun confiureEvents(recyclerView: RecyclerView, events: ArrayList<Group>) {
        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = EventsAdapter(events)
        }
    }
}

class CalendarDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.group_calendar, null))
            builder.create()
        } ?: throw IllegalStateException("Activity Cannot be Null")
    }
}

// This should be an event but  group event items have the layout we need
// No interaction on this part
class EventsAdapter (private val events:ArrayList<Group>):
    RecyclerView.Adapter<EventsAdapter.ContactHolder>() {

    class ContactHolder(v: View) : RecyclerView.ViewHolder(v) {
        val photoView = v.findViewById<CircleImageView>(R.id.photo)
        val nameView = v.findViewById<TextView>(R.id.name)
        val summaryView = v.findViewById<TextView>(R.id.summary)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.group_item, parent, false)

        return ContactHolder(layout)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val item = events[position]
        holder.photoView.setImageResource(item.icon)
        holder.nameView.text = item.name
        holder.summaryView .text = item.summary
    }

    override fun getItemCount() = events.size
}

class ContactsAdapter (private val users:ArrayList<User>):
    RecyclerView.Adapter<ContactsAdapter.ContactHolder>() {

    class ContactHolder(v: View) : RecyclerView.ViewHolder(v) {
        val photoView = v.findViewById<CircleImageView>(R.id.photo)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.group_contact, parent, false)

        return ContactHolder(layout)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val user = users[position]
        holder.photoView.setImageResource(user.photo)
    }

    override fun getItemCount() = users.size
}
