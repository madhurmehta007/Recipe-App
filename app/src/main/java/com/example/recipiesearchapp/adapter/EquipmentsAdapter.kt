package com.example.recipiesearchapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipiesearchapp.R
import com.example.recipiesearchapp.databinding.ItemEquipmentsBinding
import com.example.recipiesearchapp.models.Equipment

class EquipmentsAdapter(val context: Context,
                        val equipments:ArrayList<Equipment>
): RecyclerView.Adapter<EquipmentsAdapter.EquipmentsViewHolder>() {

    class EquipmentsViewHolder(val binding: ItemEquipmentsBinding, context: Context):
        RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemEquipmentsBinding.inflate(layoutInflater, parent, false)

        return EquipmentsViewHolder(binding, parent.context)
    }


    override fun onBindViewHolder(holder: EquipmentsViewHolder, position: Int) {
        val equipment = equipments[position]

        if (equipment.image.isEmpty()){
            Glide.with(context).load(R.drawable.ic_placeholder).into(holder.binding.ivEquipmentImage)
        }else{
            Glide.with(context).load(equipment.image).into(holder.binding.ivEquipmentImage)
        }

        holder.binding.tvEquipmentName.text = equipment.name

    }

    override fun getItemCount(): Int {
        return equipments.size
    }
}