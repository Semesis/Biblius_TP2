package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.models.Succursale
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.httpGet
import kotlinx.android.synthetic.main.fragment_succursale_details.*


/**
 * A simple [Fragment] subclass.
 */
class SuccursaleDetailsFragment : Fragment() {

    private lateinit var href : String

    override fun onCreate(savedInstanceState: Bundle?) {

        href = arguments!!.getString(HREF)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        href.httpGet().responseJson { request, response, result ->
            when (response.httpStatusCode) {
                200 -> {
                    val succursale = Succursale(result.get())

                    Toast.makeText(activity.applicationContext, succursale.appelatif, Toast.LENGTH_LONG).show()
                    // Construction de l'interface graphique
                    lblAppelatifSuccursaleDetail.text = succursale.appelatif
                    lblAdresseSuccursaleDetail.text = succursale.adresse
                    lblVilleSuccursaleDetail.text = succursale.ville
                    lblProvinceSuccursaleDetail.text = succursale.province
                    lblCodePostalSuccursaleDetail.text = succursale.codePostal
                    lblTelephoneSuccursaleDetail.text = succursale.telephone
                    lblTelecopieurSuccursaleDetail.text = succursale.telecopieur
                    lblInformationSuccursaleDetail.text = succursale.information
                }
                404 -> {
                    //TODO: Erreur
                }
            }
        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_succursale_details, container, false)
    }

    companion object {
        private val HREF = "href"

        fun newInstance(href: String) : SuccursaleDetailsFragment {
            val fragment = SuccursaleDetailsFragment()
            val args = Bundle()

            args.putString(HREF, href)

            fragment.arguments = args
            return fragment
        }
    }

}// Required empty public constructor
