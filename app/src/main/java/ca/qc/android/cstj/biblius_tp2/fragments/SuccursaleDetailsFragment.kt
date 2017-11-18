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



class SuccursaleDetailsFragment : Fragment() {

    private lateinit var href : String

    override fun onCreate(savedInstanceState: Bundle?) {

        href = arguments!!.getString(HREF)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        href.httpGet().responseJson { request, response, result ->
            when (response.statusCode) {
                200 -> {
                    val succursale = Succursale(result.get())

                    // Construction de l'interface graphique
                    lblAppelatifSuccursaleDetail.text = succursale.appelatif
                    lblAdresseSuccursaleDetail.text = succursale.adresse
                    lblVilleSuccursaleDetail.text = succursale.ville
                    lblProvinceSuccursaleDetail.text = " (${succursale.province})"
                    lblCodePostalSuccursaleDetail.text = "${succursale.codePostal.substring(0,3)} ${succursale.codePostal.substring(3,6)}"
                    lblTelephoneSuccursaleDetail.text = "${succursale.telephone.substring(0,3)}-${succursale.telephone.substring(3,6)}-${succursale.telephone.substring(6)}"
                    lblTelecopieurSuccursaleDetail.text = "${succursale.telecopieur.substring(0,3)}-${succursale.telecopieur.substring(3,6)}-${succursale.telecopieur.substring(6)}"
                    lblInformationSuccursaleDetail.text = succursale.information
                }
                404 -> {
                    Toast.makeText(context, "Ressource non trouvée!", Toast.LENGTH_LONG).show()
                }
                500 -> {
                    Toast.makeText(context, "Erreur interne du serveur serveur!", Toast.LENGTH_LONG).show()
                }
                503 -> {
                    Toast.makeText(context, "Service temporairement indisponible!", Toast.LENGTH_LONG).show()
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


}// Besoin d'un constructeur publique vide
