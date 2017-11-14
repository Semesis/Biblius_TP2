package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.adapters.CommentaireRecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.models.Commentaire
import ca.qc.android.cstj.biblius_tp2.models.Livre
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_livre_detail.*


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
        //val view = inflater.inflate(R.layout.fragment_livre_detail, container, false)
        href += "?expand=commentaires"
        href.httpGet().responseJson{ request, response, result ->
            when (response.statusCode){
                200 -> {
                    val livre = Livre(result.get())
                    lblAuteurDetail.text = livre.auteur
                    lblPrixDetail.text = livre.prix.toString()
                    lblSujetDetail.text = livre.sujet
                    lblISBNDetail.text = "ISBN: " + livre.isbn
                    //Picasso.with(imgLivreDetail.context).load(urlLivre).centerCrop().fit().into(imgLivreDetail)

                    createCommentaireList(result.get())

                    livre.commentaires = commentaires

                    listCommentaire.layoutManager = LinearLayoutManager(context)
                    listCommentaire.adapter = CommentaireRecyclerViewAdapter(livre.commentaires)
                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_livre_detail, container, false)
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
