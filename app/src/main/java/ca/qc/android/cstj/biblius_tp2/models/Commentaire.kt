package ca.qc.android.cstj.biblius_tp2.models

import com.github.kittinunf.fuel.android.core.Json
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose

/**
 * Created by Administrateur on 2017-11-14.
 */
data class Commentaire(var dateCommentaire: String,  @Expose var auteurCommentaire: String, @Expose var message: String, @Expose var etoile: Int ) {
    constructor(json:Json) :this(json.obj().getString("dateCommentaire"), json.obj().getString("auteurCommentaire"), json.obj().getString("message"), json.obj().getInt("etoile"))


    fun toJson():String{
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(this)
    }
}