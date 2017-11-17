package ca.qc.android.cstj.biblius_tp2.fragments

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.adapters.LivreCategorieRecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.models.Livre
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

/**
 * Created by Administrateur on 2017-11-07.
 */
class LivreListFragment : Fragment() {
    private var mColumnCount = 1
    private var mListener: OnListFragmentInteractionListener? = null
    private var livres = mutableListOf<Livre>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments != null){
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_livre_categorie_list, container, false)

        if (view is RecyclerView) {
            val context = view.getContext()

            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = LivreCategorieRecyclerViewAdapter(livres, mListener)
            val urlLivres = arguments.getString(HREF) + "/livres"
            urlLivres.httpGet().responseJson{ request, response, result ->
                when(response.statusCode){
                    200 -> {
                        createListLivre(result.get())
                        view.adapter.notifyDataSetChanged()
                    }
                    500 -> {

                    }
                }
            }
        }
        return view
    }

    fun createListLivre(json: Json){
        livres.clear()
        val tabJson = json.array()

        for (i in 0.. (tabJson.length() - 1 )){
            livres.add(Livre(Json(tabJson[i].toString())))
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
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
        fun onLivreCategorieFragmentInteraction(livre: Livre?)
    }

    companion object {
        private val ARG_COLUMN_COUNT = "column-count"
        private val HREF = "href"

        fun newInstance(columnCount: Int, href: String ): LivreListFragment {
            val fragment = LivreListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            args.putString(HREF, href)
            fragment.arguments = args
            return fragment
        }
    }
}
