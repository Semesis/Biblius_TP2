package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Classe contenant les informations pour les catégories
 */
class Categorie(jsonObject: Json) : Item() {

    var nom : String = jsonObject.obj().getString("categorie")
    var href : String = jsonObject.obj().getString("href")

    /**
    * Fonction qui permet d'afficher le nom d'une catégorie.
    */
    override fun getAffichage() : String {
        return nom
    }



}