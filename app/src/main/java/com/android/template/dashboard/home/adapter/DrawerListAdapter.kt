package com.android.template.dashboard.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.android.template.R

/** Adapter for navigation drawer elements */
class DrawerListAdapter : BaseExpandableListAdapter(){

    private val mListDataHeader: MutableList<ExpandedMenuModel> = ArrayList()

    override fun getGroupCount(): Int {
        return mListDataHeader.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return mListDataHeader[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return null
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var viewConvert = convertView

        val mPosition = getGroup(groupPosition) as ExpandedMenuModel

        if (viewConvert == null) {
            val layoutInflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            viewConvert = layoutInflater.inflate(R.layout.item_side_navigation, null)
        }

        val title = viewConvert!!.findViewById<TextView>(R.id.tvSideItem)
        val icon = viewConvert.findViewById<ImageView>(R.id.iv_icon_item_side_nav)

        title.text = mPosition.title
        icon.setImageResource(mPosition.icon)

        return viewConvert

    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View? {
        return null
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    internal inner class ExpandedMenuModel(
        val title : String,
        val icon : Int
    )

    init {
        mListDataHeader.add(ExpandedMenuModel("Home", R.drawable.ic_home))
        mListDataHeader.add(ExpandedMenuModel("Change Password", R.drawable.ic_lock))
        mListDataHeader.add(ExpandedMenuModel("Logout", R.drawable.ic_logout))
    }

}