package com.example.timehunter

import android.net.Uri
import java.net.URI

object ContactsData{
    val one = User("Alex Adams",  R.mipmap.p1 )
    val two = User("Allisa Lin", R.mipmap.p2)
    val three = User("Big sis", R.mipmap.p3)
    val four = User("Bobby Fletcher", R.mipmap.p4)
    val five = User("Dad", R.mipmap.p5)
    val six = User("Dottie Bruce", R.mipmap.p6)
    val seven = User("Jenna Corine", R.mipmap.p7)
    val eight = User("Leatrice Tory", R.mipmap.p8)
    val nine = User("Momma Bear", R.mipmap.p9)
    val ten = User("Steven Calahone", R.mipmap.p10)
    val eleven = User("Thompson Torioni", R.mipmap.p11)
    val twelve = User("Will Logan", R.mipmap.p12)
    var contacts = arrayListOf(one,two,three,four,five,six,seven,eight,nine,ten,eleven,twelve)
}
// In an actual app this would be a database.
// But we need some dummy data and a DB is too much work

object GroupPhoto{
    val groupIcons = ArrayList<Uri?>()
}

object GroupsData{
    private val hci_contacts= arrayListOf(ContactsData.one,ContactsData.twelve)
    private  val uoit_contacts= ContactsData.contacts
    private val hci_event = arrayListOf(Group("HCI Study Date", "Vote on the HCI study date", icon = R.drawable.hci ))

    private val hciGroup = Group("HCI","group to study HCI",R.drawable.hci, hci_contacts, events = hci_event)
    private val uoitGroup = Group("UOIT","Group from UOIT",R.drawable.uoit, uoit_contacts)


    var groups = arrayListOf(hciGroup, uoitGroup)
}

object CarouselData{
    val beerEvent = GroupEventItem("Beer Night", "This Friday Night 9pm", R.drawable.beer)
    val raptorEvent = GroupEventItem("Raptors Game", "This Saturday 7:30 pm", R.drawable.raptors)
    val hciGroup = GroupEventItem("HCI Study Group", "December 3rd 12:00pm", R.drawable.hci)

    var widgets = arrayListOf(beerEvent, raptorEvent, hciGroup)
}
 object NotificationsData{
     val beerNotification = Notification("Confirm Pickup", R.drawable.beer)
     val raptorNotification = Notification("Vote on HCI Study Day", R.drawable.hci)
     val HCINotification = Notification("All Riders Confirmed", R.drawable.raptors)

     var notifications = arrayListOf(beerNotification, raptorNotification, HCINotification)
 }


