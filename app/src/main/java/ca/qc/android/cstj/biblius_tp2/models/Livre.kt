package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Created by Administrateur on 2017-11-07.
 */
class Livre(jsonObject : Json) {
    var titre : String = jsonObject.obj().getString("titre")
    var prix: Double = jsonObject.obj().getDouble("prix")
    var auteur: String = jsonObject.obj().getString("auteur")
    var sujet: String = jsonObject.obj().getString("sujet")
    var isbn: String = jsonObject.obj().getString("isbn")
    var image: String = jsonObject.obj().getString("image")
    var href: String = jsonObject.obj().getString("href")
}