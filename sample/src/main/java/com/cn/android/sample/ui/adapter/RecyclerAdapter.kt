package com.cn.android.sample.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cn.android.sample.R

abstract class BaseRecyclerAdapter public constructor(mLayoutId: Int): RecyclerView.Adapter<BaseRecyclerAdapter.RecyclerHolder>() {

    private var data: List<Any>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var mLayoutId: Int? = null

    init {
        this.mLayoutId = mLayoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        var view = LayoutInflater.from(parent.context).inflate(mLayoutId?: R.layout.item_base_recycler, parent, false);
        return RecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        convert(holder, data?.get(position), position)
    }

    override fun getItemCount(): Int {
        return data?.size?: 0
    }

    abstract fun convert(holder: RecyclerHolder, item: Any?, position: Int)

    class RecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}