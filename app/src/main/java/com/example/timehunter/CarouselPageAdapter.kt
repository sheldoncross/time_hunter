package com.example.timehunter

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import de.hdodenhof.circleimageview.CircleImageView

class GroupEventsFragment(private val groupEventItem: GroupEventItem): Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carousel_group_event, container, false)
        val titleView = view.findViewById(R.id.title) as TextView
        val contentView = view.findViewById(R.id.content) as TextView
        val imageView = view.findViewById(R.id.image) as ImageView
        titleView.text = groupEventItem.title
        contentView.text = groupEventItem.description
        imageView.setImageResource(groupEventItem.iconId)
        return view
    }
    companion object{
        fun newInstance(groupEventItem: GroupEventItem) =  GroupEventsFragment(groupEventItem)
    }
}

class CalendarFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_carousel_calendar, container, false)
    }

    companion object {
        fun newInstance() = CalendarFragment()
    }
}

class IconPageAdapter (private val items:ArrayList<GroupEventItem>):
    RecyclerView.Adapter<IconPageAdapter.IconHolder>() {

    class IconHolder(val imageView: CircleImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): IconHolder {
        val imageView  = LayoutInflater.from(parent.context)
            .inflate(R.layout.carousel_icon,parent,false) as CircleImageView
        return  IconHolder(imageView)
    }

    override fun onBindViewHolder(holder: IconHolder, position: Int) {
        val id :Int = items[position].iconId
        holder.imageView.setImageResource(id)
    }

    override fun getItemCount() = items.size
}

class CarouselPageAdapter (f:Fragment, private val fragments:ArrayList<Fragment>): FragmentStateAdapter(f) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}

class HorizontalMarginItemDecoration(context: Context, @DimenRes horizontalMarginInDp: Int): RecyclerView.ItemDecoration() {
    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.right = horizontalMarginInPx
        outRect.left = horizontalMarginInPx
    }
}
