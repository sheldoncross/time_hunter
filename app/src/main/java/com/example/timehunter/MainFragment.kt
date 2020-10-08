package com.example.timehunter

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var iconView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_main, container, false)
        viewPager = layout.findViewById(R.id.view_pager)
        iconView = layout.findViewById(R.id.icon_group)
        val notificationList = layout.findViewById<RecyclerView>(R.id.notifications)
        val context = requireContext()

        val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
        val currentItemHorizontalMarginPx = resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
        val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
        val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
            page.translationX = -pageTranslationX * position
            page.scaleY = 1 - (0.5f * abs(position))
        }

        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        layoutManger.scrollToPosition(0)

        val notificationAdapter = NotificationAdapter(NotificationsData.notifications)
        val itemDecorator = VerticalMarginItemDecoration(
            context,
            R.dimen.viewpager_current_item_horizontal_margin
        )

        notificationList.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = notificationAdapter
            //addItemDecoration(itemDecorator)
        }

        prepCarousel(pageTransformer, context, CarouselData.widgets)
        prepIcons(context, CarouselData.widgets)

        return layout
    }

    fun prepIcons(context: Context,
        items: ArrayList<GroupEventItem>) {
        val itemDecoration = HorizontalMarginItemDecoration(
            context,
            R.dimen.viewpager_current_icon_horizontal_margin
        )

        val layoutManger = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        layoutManger.scrollToPosition(1)
        val iconAdapter = IconPageAdapter(items)

        iconView.apply {
            setHasFixedSize(true)
            layoutManager = layoutManger
            adapter = iconAdapter
        }
        iconView.addItemDecoration(itemDecoration)
    }

    fun prepCarousel(
        pageTransformer: ViewPager2.PageTransformer,
        context: Context,
        items: ArrayList<GroupEventItem>
    ) {

        val fragments: ArrayList<Fragment> =
            ArrayList(items.map { GroupEventsFragment.newInstance(it) })

        val itemDecoration = HorizontalMarginItemDecoration(
            context,
            R.dimen.viewpager_current_item_horizontal_margin
        )
        val adapter = CarouselPageAdapter(this, fragments)
        viewPager.adapter = adapter
        viewPager.currentItem = 1
        viewPager.offscreenPageLimit = 1
        viewPager.addItemDecoration(itemDecoration)
        viewPager.setPageTransformer(pageTransformer)
    }
}
class VerticalMarginItemDecoration(context: Context, @DimenRes verticalMarginInDp: Int): RecyclerView.ItemDecoration() {
    private val vert: Int =  context.resources.getDimension(verticalMarginInDp).toInt()

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom=vert
    }
}
