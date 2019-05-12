package com.nansty.sheetspad

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar

class NoteDetailActivity : AppCompatActivity() {

    //Equivalent du static en java
    //permets de créer des propriétés/méthodes accessibles même si aucune instance de cette classe n'existe
    companion object {
        val REQUEST_EDIT_NOTE = 1
        val EXTRA_NOTE = "note"
        val EXTRA_NOTE_INDEX = "noteIndex"
        val ACTION_SAVE_NOTE = "com.nansty.sheetspad.actions.ACTION_SAVE_NOTE"
        val ACTION_DELETE_NOTE = "com.nansty.sheetspad.actions.ACTION_DELETE_NOTE"

    }
    //variable initialisable plus tard
    lateinit var note: Note
    //variable noteIndex de Type Int prennant comme valeur -1
    var noteIndex: Int = -1

    lateinit var titleView: TextView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        //On recupere notre toolbar de notre layout
        val toolbar = findViewById(R.id.toolbar) as android.support.v7.widget.Toolbar
        setSupportActionBar(toolbar)
        //On rajoute notre bouton back pour reveinir vers NoteListAActivyty , true permet d'afficher la fleche
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        note = intent.getParcelableExtra<Note>(EXTRA_NOTE)
        noteIndex = intent.getIntExtra(EXTRA_NOTE_INDEX, -1)

        //Recupere nos vue
        titleView = findViewById(R.id.title)
        textView = findViewById(R.id.text)

        //On relie nos vue a nos données
        titleView.text = note.title
        textView.text = note.text
    }

    //Implemente un nouveau Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_note_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // when est similaire a un switch
        when (item.itemId){
            //Si la valeur de itemId est action_save
            R.id.action_save -> {
                saveNote()
                return true
            }
            //Si la valeur de itemId est action_delete
            R.id.action_delete -> {
                showConfirmDeleteNoteDialog()
                return true
            }
            //Si aucun des choix n'est présent alors on rentre dans le SINON
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun showConfirmDeleteNoteDialog() {
        //Instancie notre dialogue et on passe notre titre de la notre 
        val confirmFragment = ConfirmDeleteNoteDialogFragment(note.title)
        //defninre notre listener
        confirmFragment.listener = object : ConfirmDeleteNoteDialogFragment.ConfirmDeleteDialogListener{
            //Implementre les deux fonctions
            override fun onDialogPositiveClick() {
                deleteNote()
            }

            override fun onDialogNegativeClick() {
            }


        }

        confirmFragment.show(supportFragmentManager, "confirmDeleteDialog")
    }


    private fun saveNote() {
        //On recupere la valeur present dans le textView title et text
        note.title = titleView.text.toString()
        note.text = titleView.text.toString()
        //On passe en argusment d'intent l'action qui va etre utiliser
        intent = Intent(ACTION_SAVE_NOTE)
        //On ajoute dans notre extra une clé à laquelle on associe une valeur
        intent.putExtra(EXTRA_NOTE, note as Parcelable)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun deleteNote(){
        //On passe en argusment d'intent l'action qui va etre utiliser
        intent = Intent(ACTION_DELETE_NOTE)
        intent.putExtra(EXTRA_NOTE_INDEX, noteIndex)
        setResult(Activity.RESULT_OK, intent)
        finish()

    }
}
