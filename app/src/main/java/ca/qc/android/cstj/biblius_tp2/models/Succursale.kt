package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json

/**
 * Created by Administrateur on 2017-11-02.
 */
class Succursale(jsonObject: Json) : Item() {
    override fun getAffichage() : String {
        return nom
    }

    var nom : String = jsonObject.obj().getString("appelatif")
}