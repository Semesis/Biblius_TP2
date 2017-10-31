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
import ca.qc.android.cstj.biblius_tp2.helpers.SUCCURSALE
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
    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_succursale_list, container, false)

        // Set adapter
        if(view is RecyclerView) {
            val context = view.getContext()
            if(mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            SUCCURSALE.httpGet().responseJson { request, response, result ->
                view.adapter = RecyclerViewAdapter(createSuccursaleList(result.get()), mListener)
            }
        }
        return view
    }

    fun createSuccursaleList(json: Json) : List<Succursale> {
        val succursales = mutableListOf<Succursale>()
        val tabJson = json.array()

        for (i in 0.. (tabJson.length() -1 )) {
            succursales.add(Succursale(Json(tabJson[i].toString())))
        }
        return succursales
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListSuccursaleFragmentInteraction(succursale: Succursale?)
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
