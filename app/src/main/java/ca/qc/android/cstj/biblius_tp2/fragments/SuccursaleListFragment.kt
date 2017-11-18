package ca.qc.android.cstj.biblius_tp2.fragments

import android.content.Context
import android.net.Uri
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
import ca.qc.android.cstj.biblius_tp2.adapters.RecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.helpers.SUCCURSALES_URL
import ca.qc.android.cstj.biblius_tp2.helpers.TP1_WEB_SERVICES
import ca.qc.android.cstj.biblius_tp2.models.Succursale
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet


class SuccursaleListFragment : Fragment() {

    private var mColumnCount = 1
    private var mListener: OnListItemFragmentInteractionListener? = null
    private var succursales = mutableListOf<Succursale>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set l'adapter
        if(view is RecyclerView) {
            val context = view.getContext()
            if(mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = RecyclerViewAdapter(succursales, mListener)
            // On fait appel à l'API pour aller chercher les succursales
            SUCCURSALES_URL.httpGet().responseJson { request, response, result ->
                when(response.statusCode) {
                    200 -> {
                        createSuccursaleList(result.get())
                        view.adapter.notifyDataSetChanged()
                    }
                    500 -> {
                        Toast.makeText(context, "Erreur de connection au serveur!", Toast.LENGTH_LONG).show()
                    }
                    503 -> {
                        Toast.makeText(context, "Service temporairement indisponible!", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
        return view
    }

    // Fonction qui crée une liste de succursales et qui les ajoute à la variable succursales. Ceci sera utilisé lors de la création de la vue
    fun createSuccursaleList(json: Json) {

        succursales.clear()
        val tabJson = json.array()

        for (i in 0.. (tabJson.length() -1 )) {
            succursales.add(Succursale(Json(tabJson[i].toString())))
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


    companion object {
        private val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) : SuccursaleListFragment {
            val fragment = SuccursaleListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args

            return fragment
        }
    }
}// Nécessite un constructeur public vide.
