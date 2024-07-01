package com.rafiul.lovenotes.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    private val bindingFactory: (LayoutInflater, ViewGroup, Boolean) -> VB
) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(diffCallback) {

    abstract fun bind(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = bindingFactory(inflater, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
        bind(holder.binding, getItem(position))
    }

    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
}


//abstract class BaseAdapter<T : Any, VB : ViewBinding>(
//    diffCallback: DiffCallback<T>,
//    private val bindingFactory: (LayoutInflater, ViewGroup, Boolean) -> VB
//) : ListAdapter<T, BaseAdapter.BaseViewHolder<VB>>(diffCallback) {
//
//    abstract fun bind(binding: VB, item: T)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = bindingFactory(inflater, parent, false)
//        return BaseViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
//        val item = getItem(position)
//        bind(holder.binding, item)
//    }
//
//    class BaseViewHolder<VB : ViewBinding>(val binding: VB) : RecyclerView.ViewHolder(binding.root)
//
//    abstract class DiffCallback<T : Any>(
//        private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean
//    ) : DiffUtil.ItemCallback<T>() {
//        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = areItemsTheSame(oldItem, newItem)
//        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
//    }
//}





