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


/**
 * A simple [Fragment] subclass.
 * Use the [LivreDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivreDetailFragment : Fragment() {

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
                    val livre = Livre(result.get())
                    lblAuteurDetail.text = livre.auteur
                    lblPrixDetail.text = livre.prix.toString()
                    lblSujetDetail.text = livre.sujet
                    lblISBNDetail.text = "ISBN: " + livre.isbn
                    val urlLivre = "${TP1_WEB_SERVICES}${livre.image}"
                    Picasso.with(imgLivreDetail.context).load(urlLivre).centerCrop().fit().into(imgLivreDetail)

                    createCommentaireList(result.get())

                    livre.commentaires = commentaires

                    listCommentaire.layoutManager = LinearLayoutManager(context)
                    listCommentaire.adapter = CommentaireRecyclerViewAdapter(livre.commentaires)
                }
            }
        }

        view.btnAjouterCommentaire.setOnClickListener {

            if(txtNomPrenom.text.toString() != "" && txtCommentaire.text.toString() != "" && (rtbEtoile.rating > 0 && rtbEtoile.rating <=5)) {
                val commentaire = Commentaire("",
                        txtNomPrenom.text.toString(),
                        txtCommentaire.text.toString(),
                        rtbEtoile.rating.toInt())
                Toast.makeText(context, commentaire.toJson(), Toast.LENGTH_LONG).show()
                val hrefPost = href + "/commentaires"
                hrefPost.httpPost()
                        .header("Content-Type" to "application/json")
                        .body(commentaire.toJson()).responseJson { _, response, _ ->
                    when(response.statusCode) {
                        201 -> {
                            updateCommentaires()
                        }
                    }
                }
            }
            else {
                Toast.makeText(context,"Vous devez remplir tous les champs", Toast.LENGTH_LONG).show()
            }

        }

        // Inflate the layout for this fragment
        return view
    }

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

    fun createCommentaireList(json: Json){
        commentaires.clear()
        val tabJson = json.obj().getJSONArray("commentaires")

        for ( i in 0.. (tabJson.length() - 1)){
            commentaires.add(Commentaire(Json(tabJson[i].toString())))
        }

    }

    companion object {
        private val HREF = "href"


        fun newInstance(href:String): LivreDetailFragment {
            val fragment = LivreDetailFragment()
            val args = Bundle()
            args.putString(HREF,href)
            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
