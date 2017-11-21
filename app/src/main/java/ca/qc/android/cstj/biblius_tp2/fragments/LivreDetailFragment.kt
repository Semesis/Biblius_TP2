package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.adapters.CommentaireRecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.helpers.TP1_WEB_SERVICES
import ca.qc.android.cstj.biblius_tp2.models.Commentaire
import ca.qc.android.cstj.biblius_tp2.models.Livre
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_livre_detail.*
import kotlinx.android.synthetic.main.fragment_livre_detail.view.*
import java.util.*


// Fragment qui permettra d'afficher les détails d'un livre
class LivreDetailFragment : Fragment() {

    // Cette variable n'est pas nécéssairement initialisé au début
    private lateinit var href: String
    private var commentaires = mutableListOf<Commentaire>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        href = arguments!!.getString(HREF)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_livre_detail, container, false)
        val hrefLivres = href + "?expand=commentaires"
        hrefLivres.httpGet().responseJson{ request, response, result ->
            when (response.statusCode){
                200 -> {
                    // On veut ajouter les information à la vue du fragment.
                    val livre = Livre(result.get())
                    lblAuteurDetail.text = livre.auteur
                    lblPrixDetail.text = "${livre.prix.toString()} $"
                    lblSujetDetail.text = livre.sujet
                    lblISBNDetail.text = "ISBN: " + livre.isbn
                    val urlLivre = "${TP1_WEB_SERVICES}${livre.image}"
                    Picasso.with(imgLivreDetail.context)
                            .load(urlLivre)
                            .resize(300,400)
                            .into(imgLivreDetail)

                    createCommentaireList(result.get())

                    livre.commentaires = commentaires

                    // Ici, on appelle l'adapteur de Commentaire afin de gérer la liste des commentaires d'un livre.
                    listCommentaire.layoutManager = LinearLayoutManager(context)
                    listCommentaire.adapter = CommentaireRecyclerViewAdapter(livre.commentaires)
                }
                404 -> {
                    Toast.makeText(context, "Ressource non trouvée!", Toast.LENGTH_LONG).show()
                }
                500 -> {
                    Toast.makeText(context, "Erreur interne du serveur serveur!", Toast.LENGTH_LONG).show()
                }
                503 -> {
                    Toast.makeText(context, "Service temporairement indisponible!", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Fonction pour ajouter un commentaire
        view.btnAjouterCommentaire.setOnClickListener {
            // On vérifie si le commentaire est trop long.
            if (txtCommentaire.text.toString().length <= 255) {
                // On vérifie si le nom est trop long
                if (txtNomPrenom.text.toString().length <= 30) {
                    // On vérifie si tous les champs sont remplis
                    if (txtNomPrenom.text.toString() != "" && txtCommentaire.text.toString() != "" && (rtbEtoile.rating > 0 && rtbEtoile.rating <= 5)) {
                        // On crée un commentaire avec les informations saisies par l'utilisateur.
                        val commentaire = Commentaire("",
                                txtNomPrenom.text.toString(),
                                txtCommentaire.text.toString(),
                                rtbEtoile.rating.toInt())
                        Toast.makeText(context, "Commentaire ajouté!", Toast.LENGTH_LONG).show()
                        val hrefPost = href + "/commentaires"
                        // On envoit le commentaire créé en BD
                        hrefPost.httpPost()
                                .header("Content-Type" to "application/json")
                                // Les "_" permetde remplacer les champs vides qui ne sont pas utilisés.
                                .body(commentaire.toJson()).responseJson { _, response, _ ->
                            // Puis on gère le status qui est retourné, afin de faire la bonne action.
                            when (response.statusCode) {
                                201 -> {
                                    updateCommentaires()
                                    // Vide les champs
                                    txtNomPrenom.text.clear()
                                    txtCommentaire.text.clear()
                                    rtbEtoile.rating = 0.toFloat()
                                }
                            }
                        }
                    }
                    // Si un des champs n'est pas rempli, on affiche un message à l'utilisateur lui indiquant qu'un des champs est vide.
                    else {
                        Toast.makeText(context, "Vous devez remplir tous les champs", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, "Le nom est trop long!", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(context, "Le commentaire est trop long!", Toast.LENGTH_LONG).show()
            }

        }
        return view
    }

    // Mise à jour de la liste des commentaires lors de l'ajout d'un nouveau commentaire
    fun updateCommentaires() {
        val hrefCommentaire = href + "?expand=commentaires"
        hrefCommentaire.httpGet().responseJson{ request, response, result ->
            when (response.statusCode){
                200 -> {
                    val livre = Livre(result.get())

                    createCommentaireList(result.get())
                    livre.commentaires = commentaires
                    listCommentaire.adapter.notifyDataSetChanged()
                }
            }
        }
    }

    // On crée la liste des commentaires
    fun createCommentaireList(json: Json){
        commentaires.clear()
        val tabJson = json.obj().getJSONArray("commentaires")

        for ( i in 0.. (tabJson.length() - 1)){
            commentaires.add(Commentaire(Json(tabJson[i].toString())))
        }

    }

    companion object {
        // Ceci est le href contenant le uuid du livre sélectionné
        private val HREF = "href"


        fun newInstance(href:String): LivreDetailFragment {
            val fragment = LivreDetailFragment()
            val args = Bundle()
            args.putString(HREF,href)
            fragment.arguments = args
            return fragment
        }
    }

}// Nécessite un constructeur vide.
