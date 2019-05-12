package com.nansty.sheetspad

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.nansty.sheetspad.utils.deleteNote
import com.nansty.sheetspad.utils.loadNotes

//MainActivity va etre notre Listener
class MainActivity : AppCompatActivity(), View.OnClickListener {


    // variable qui peut etre initialisée plus tard
    //liste qui peut etre modifier de type class Note
    lateinit var notes: MutableList<Note>
    //variale de type class NoteAdapter, Source de donnée NoteA
    lateinit var adapter: NoteAdapter
    //variable de type layout CoordinatorLayout
    lateinit var coordinatorLayout : CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //On recupere notre toolbar et on l'initialise pour l'utiliser
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //
        findViewById<FloatingActionButton>(R.id.create_note_fab).setOnClickListener(this)


        notes = loadNotes(this)
        //en Param, notes pour les donnée et this pour le OnClik
        adapter = NoteAdapter(notes, this)

        //On recupere notre recyclerView et affecter l'adapter pour qu'il puisse  l'alimenter grace a lui
        val recyclerView = findViewById(R.id.notes_recycler_view) as RecyclerView
        //Comment la liste doit etre ( verticale ou horizontale ) par defaut verticale
        recyclerView.layoutManager = LinearLayoutManager(this)
        //Affecte notre adapter a notre recyclerView
        recyclerView.adapter = adapter

        coordinatorLayout = findViewById(R.id.coordinator_layout) as CoordinatorLayout

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK || data == null){
            return
        }
        when(requestCode){
            NoteDetailActivity.REQUEST_EDIT_NOTE -> processEditNoteResult(data)
        }
    }

    

    private fun processEditNoteResult(data: Intent) {
        val noteIndex = data.getIntExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, -1)

        //On recupere l'action dans l'intent
        when(data.action){
            NoteDetailActivity.ACTION_SAVE_NOTE ->{
                val note = data.getParcelableExtra<Note>(NoteDetailActivity.EXTRA_NOTE)
                saveNote(note, noteIndex)
            }
            NoteDetailActivity.ACTION_DELETE_NOTE ->{
                deleteNote(noteIndex)
            }
        }

    }

    private fun deleteNote(noteIndex: Int) {
        //On teste l'index de la note
        if(noteIndex < 0){
            //on ne fait rien
            return
        }
        val note = notes.removeAt(noteIndex)
        deleteNote(this, note)
        adapter.notifyDataSetChanged()

        Snackbar.make(coordinatorLayout, "${note.title} supprimé", Snackbar.LENGTH_SHORT)
    }


    private fun saveNote(note: Note, noteIndex: Int){
        if (noteIndex < 0){
            notes.add(0, note)
        }else{
            notes[noteIndex] = note
        }

        adapter.notifyDataSetChanged()
    }

    

    override fun onClick(view: View) {
        if(view.tag != null){
            showNoteDetail(view.tag as Int)
        }else{
            when(view.id){
                R.id.create_note_fab -> createNewNote()
            }
        }
    }

    private fun createNewNote() {
        showNoteDetail(-1)
    }

    fun showNoteDetail(noteIndex: Int){
        val note = if (noteIndex < 0) {Note()} else {notes[noteIndex]}

        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE, note as Parcelable)
        intent.putExtra(NoteDetailActivity.EXTRA_NOTE_INDEX, noteIndex)
        startActivityForResult(intent, NoteDetailActivity.REQUEST_EDIT_NOTE)

    }
}
