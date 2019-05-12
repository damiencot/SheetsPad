package com.nansty.sheetspad

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

//Vieholder est une classe qui permet de conserver en memoire les elements graphiques ( la vue ) 
// Un tableau de Note de type List , on utilise val pour interdire notre adapter de modifier notre tableau
//il doit juste donner les donnée au viewHolder
//
class NoteAdapter(val notes: List<Note>, val itemClickListener: View.OnClickListener ) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    //Elle prend en param une vue qu'elle doit chargée, héritedu vielhdoler par defaut de RecycleView et prend notre itemView
    //Elle conserve nos référence vers nos élèments graphiques
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById(R.id.card_view) as CardView
        val titleView = itemView.findViewById(R.id.title) as TextView
        val excerptView = itemView.findViewById(R.id.excerpt) as TextView
    }

    //Permet de crée les elements graphiques
    //On crée notre vue et on renvoie notre vueholder qui va se rattache a notre element graphique
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //l'inflater permet de convertir du fichier xml en code kotlin
        ////Recupere le layout inflater qui provient de l'element parent passé en parametre 
        val viewItem = LayoutInflater.from(parent.context)
            //Crée notre element graphique (fichier xml, parent, on ne le ratache pas a ce parent)
            .inflate(R.layout.item_note,parent, false)
        //transfere a note ViewHolder avec en paramantre notre viewItem
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag = position
        holder.titleView.text = note.title
        holder.excerptView.text = note.text
    }


    override fun getItemCount(): Int {
        return notes.size
    }

}