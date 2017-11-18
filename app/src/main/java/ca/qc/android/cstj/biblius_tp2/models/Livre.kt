package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Classe qui contient les information d'un Livre
 */
class Livre(jsonObject : Json) {
    var titre : String = jsonObject.obj().getString("titre")
    var prix: Double = jsonObject.obj().getDouble("prix")
    var auteur: String = jsonObject.obj().getString("auteur")
    var sujet: String = jsonObject.obj().getString("sujet")
    var isbn: String = jsonObject.obj().getString("isbn")
    var image: String = jsonObject.obj().getString("image")
    var href: String = jsonObject.obj().getString("href")
    var commentaires = mutableListOf<Commentaire>()

}