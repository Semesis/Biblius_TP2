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

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.adapters.RecyclerViewAdapter
import ca.qc.android.cstj.biblius_tp2.helpers.SUCCURSALES_URL
import ca.qc.android.cstj.biblius_tp2.helpers.TP1_WEB_SERVICES
import ca.qc.android.cstj.biblius_tp2.models.Succursale
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SuccursaleListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SuccursaleListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccursaleListFragment : Fragment() {

    // TODO: Rename and change types of parameters
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

        // Set adapter
        if(view is RecyclerView) {
            val context = view.getContext()
            if(mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }
            view.adapter = RecyclerViewAdapter(succursales, mListener)

            SUCCURSALES_URL.httpGet().responseJson { request, response, result ->
                createSuccursaleList(result.get())
                view.adapter.notifyDataSetChanged()
            }
        }
        return view
    }

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
}// Required empty public constructor
