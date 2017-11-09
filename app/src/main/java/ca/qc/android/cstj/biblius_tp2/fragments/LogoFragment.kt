package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.android.cstj.biblius_tp2.R
import kotlinx.android.synthetic.main.fragment_logo.view.*


/**
 * A simple [Fragment] subclass.
 * Use the [LogoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LogoFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_logo, container, false)
        view.btnCategories.setOnClickListener {
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.contentFrame, CategorieListFragment.newInstance(1))
            transaction.addToBackStack("Logo")
            transaction.commit()
        }
        view.btnSuccursales.setOnClickListener {
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.contentFrame, SuccursaleListFragment.newInstance(1))
            transaction.addToBackStack("Logo")
            transaction.commit()
        }
        return view
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LogoFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): LogoFragment {
            val fragment = LogoFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
