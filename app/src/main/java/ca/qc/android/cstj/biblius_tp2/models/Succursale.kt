package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Created by Administrateur on 2017-11-02.
 */
class Succursale(jsonObject: Json) : Item() {
    override fun getAffichage() : String {
        return appelatif
    }

    var appelatif : String = jsonObject.obj().getString("appelatif")
    var href : String = jsonObject.obj().getString("href")
    var adresse : String = jsonObject.obj().getString("adresse")
    var telephone : String = jsonObject.obj().getString("telephone")
    var telecopieur : String = jsonObject.obj().getString("telecopieur")
    var information : String = jsonObject.obj().getString("information")
    var ville : String = jsonObject.obj().getString("ville")
    var codePostal : String = jsonObject.obj().getString("codePostal")
    var province : String = jsonObject.obj().getString("province")
}