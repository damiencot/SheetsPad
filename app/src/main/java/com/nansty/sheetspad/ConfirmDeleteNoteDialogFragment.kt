package com.nansty.sheetspad

import android.app.AlertDialog
import android.os.Bundle
import android.app.Dialog
import android.content.DialogInterface
import android.support.v4.app.DialogFragment

class ConfirmDeleteNoteDialogFragment(val noteTitle: String = "") : DialogFragment() {

    //Interface qui implemente deux fonctions
    interface ConfirmDeleteDialogListener{
        fun onDialogPositiveClick()
        fun onDialogNegativeClick()
    }

    // variable mutable de type ConfirmDeleteDialogListener pouvant etre Null 
    var listener: ConfirmDeleteDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        //construction d'un Dialog qui affiche un message avec deux choix
        builder.setMessage("Etes-vous sur de supprimer la note \"$noteTitle\" ? ")
            .setPositiveButton("Supprimer", DialogInterface.OnClickListener{ dialog, id -> listener?.onDialogPositiveClick()})
            .setNegativeButton("Annuler", DialogInterface.OnClickListener{ dialog, id -> listener?.onDialogNegativeClick()})

        return builder.create()
    }
}