package ca.qc.android.cstj.biblius_tp2.models

/**
 * Fonction abstraite qui sera utilisé pour les classes Categorie et Succursale
 */
open abstract class Item {
    abstract fun getAffichage() : String

}