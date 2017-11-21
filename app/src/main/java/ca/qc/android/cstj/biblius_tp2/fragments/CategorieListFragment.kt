package ca.qc.android.cstj.biblius_tp2.fragments

import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.adapters.ItemRecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.helpers.CATEGORIES_URL
import ca.qc.android.cstj.biblius_tp2.models.Categorie
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

/**
 * Un fragment qui représente une liste de Categorie.
 * Doit implémenter l'inteface [OnListFragmentInteractionListener]
 */
class CategorieListFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListItemFragmentInteractionListener? = null
    private var categories = mutableListOf<Categorie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Initialiser l'adapteur
        if (view is RecyclerView) {
            val context = view.getContext()
            // Modifier l'affichage selon le nombre de colonnes initialisé à la création.
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = ItemRecyclerViewAdapter(categories, mListener)

            // On fait appel à l'API pour aller chercher les catégories dans la base de données. On les ajoute à la liste et on averti l'Adapter que les donnés on changé.
            CATEGORIES_URL.httpGet().responseJson { request, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createCategorieList(result.get())
                        view.adapter.notifyDataSetChanged()
                    }
                    500 -> {
                        Toast.makeText(context, "Erreur interne du serveur serveur!", Toast.LENGTH_LONG).show()
                    }
                    503 -> {
                        Toast.makeText(context, "Service temporairement indisponible!", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }
        return view
    }

    // On ajoute les catégories dans la liste afin de pouvoir les afficher.
    fun createCategorieList(json: Json) {

        categories.clear()
        val tabJson = json.array()

        for ( i in 0.. (tabJson.length() - 1)){
            categories.add(Categorie(Json(tabJson[i].toString())))
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListItemFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener{
        fun onListCategorieFragmentInteraction(categorie: Categorie?)
    }



    companion object {
        private val ARG_COLUMN_COUNT = "column-count"

        // Ici, on indique le ce que l'on veut voir lorsque l'on crée un nouvel instance du fragment.
        fun newInstance(columnCount: Int): CategorieListFragment {
            val fragment = CategorieListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
