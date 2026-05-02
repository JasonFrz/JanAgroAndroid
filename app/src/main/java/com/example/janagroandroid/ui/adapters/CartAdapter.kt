package com.example.janagroandroid.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.janagroandroid.data.local.entity.CartEntity
import com.example.janagroandroid.databinding.ItemCartBinding

class CartAdapter(
    private var items: List<CartEntity> = emptyList(),
    private val onDelete: (CartEntity) -> Unit
) : RecyclerView.Adapter<CartAdapter.VH>() {

    fun submitList(newItems: List<CartEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class VH(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CartEntity) {
            binding.tvName.text = item.productName
            binding.tvQty.text = "Qty: ${item.qty}"
            binding.tvPrice.text = "Rp ${item.price}"

            Glide.with(binding.root.context)
                .load(item.imageUrl)
                .into(binding.ivProduct)

            binding.btnDelete.setOnClickListener {
                onDelete(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}