package com.kimdo.animalmvitest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kimdo.animalmvitest.api.AnimalService
import com.kimdo.animalmvitest.databinding.AnimalItemBinding
import com.kimdo.animalmvitest.model.Animal

class AnimalListAdapter(private val animals: ArrayList<Animal>) :
RecyclerView.Adapter<AnimalListAdapter.DataViewHolder>() {

    fun newAnimals(newAnimals: List<Animal>) {
        animals.clear()
        animals.addAll(newAnimals)
        notifyDataSetChanged()
    }

    class DataViewHolder(private val binding: AnimalItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(animal: Animal) {
            binding.animalName.text = animal.name
            binding.animalLocation.text = animal.location
            val url = AnimalService.BASE_URL + animal.image
            Glide.with(binding.animalImage.context)
                .load(url)
                .into(binding.animalImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder( AnimalItemBinding.inflate( LayoutInflater.from(parent.context), parent, false) )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind( animals[position])
    }

    override fun getItemCount(): Int {
        return animals.size
    }
}