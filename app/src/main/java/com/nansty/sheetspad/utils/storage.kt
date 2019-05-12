package com.nansty.sheetspad.utils
//Fichier - Top-level function 

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.nansty.sheetspad.Note
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

//Message de LOG
private val TAG = "storage"

fun persistNote(context: Context, note: Note) : Boolean {
    var saved = true

    //
    if (TextUtils.isEmpty(note.filename)) {
        //UUID permet de generer des identifiant unique  ( nom de fichier)
        note.filename = UUID.randomUUID().toString() + ".note"
    }
    Log.i(TAG, "Saving note $note")
    //Ouverture de notre Fichier de note accessible uniquement par l'application ( MODE_PRIVATE)
    val fileOutput = context.openFileOutput(note.filename, Context.MODE_PRIVATE)
    //Ouvrire un flux de donnée
    val outputStream = ObjectOutputStream(fileOutput)
    outputStream.writeObject(note)

    return saved
}

fun loadNotes(context: Context) : MutableList<Note> {
    //Cree notre liste
    val notes = mutableListOf<Note>()
    Log.i(TAG, "Loading notes...")

    //Recuperer le dossier de notre application
    //dans lequelle nos note sont 
    val notesDir = context.filesDir
    //On itere dessus
    for(filename in notesDir.list()) {
        val note = loadNote(context, filename)
        Log.i(TAG, "Loaded note $note")
        notes.add(note)
    }
    return notes
}

fun deleteNote(context: Context, note: Note): Boolean {
    return context.deleteFile(note.filename)
}

private fun loadNote(context: Context, filename: String) : Note {
    //Ouvrire notre fichier en mode lecture
    val fileInput = context.openFileInput(filename)
    //Ouvre le flux
    val inputStream = ObjectInputStream(fileInput)
    //INITIALISE NOTRE NOTE
    val note = inputStream.readObject() as Note
    return note
}

