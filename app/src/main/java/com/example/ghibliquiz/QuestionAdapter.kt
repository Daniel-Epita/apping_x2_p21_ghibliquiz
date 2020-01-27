package com.example.ghibliquiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_item.view.*

class QuestionAdapter(val items : MutableList<Character> = mutableListOf<Character>(),
                      val correct: Int, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.charactername?.text = items.get(position).name
        holder?.characterage?.text = items.get(position).age
        if (items.get(position).gender == "Male")
            holder?.genderimage?.setImageResource(R.drawable.male)
        else
            holder?.genderimage?.setImageResource(R.drawable.female)

    }

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val charactername = view.character_name
    val characterage = view.character_age
    val genderimage = view.gender_image
}