package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Created by Administrateur on 2017-10-31.
 */
class Categorie(jsonObject: Json) : Item() {

    var nom : String = jsonObject.obj().getString("categorie")

    override fun getAffichage() : String {
        return nom
    }



}