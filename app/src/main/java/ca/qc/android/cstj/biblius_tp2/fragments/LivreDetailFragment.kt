package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.models.Livre
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_livre_detail.*


/**
 * A simple [Fragment] subclass.
 * Use the [LivreDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivreDetailFragment : Fragment() {

    private lateinit var href: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        href = arguments!!.getString(HREF)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        href.httpGet().responseJson{ request, response, result ->
            when (response.httpStatusCode){
                200 -> {
                    val livre = Livre(result.get())
                    lblAuteurDetail.text = livre.auteur
                    lblPrixDetail.text = livre.prix.toString()
                }
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_livre_detail, container, false)
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
