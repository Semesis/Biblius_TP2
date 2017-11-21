package ca.qc.android.cstj.biblius_tp2.fragments


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.android.cstj.biblius_tp2.MainActivity

import ca.qc.android.cstj.biblius_tp2.R
import kotlinx.android.synthetic.main.fragment_logo.view.*


class LogoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // On veut crée une vue pour ce fragment
        var view = inflater.inflate(R.layout.fragment_logo, container, false)
        // On ajoute un comportement pour le bouton des catégories
        view.btnCategories.setOnClickListener {
            // Création d'une transaction qui sera une nouvelle instance du fragment de la liste des catégories et qui sera ajouté à la pile
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentFrame, CategorieListFragment.newInstance(1))
            transaction.addToBackStack("Logo")
            transaction.commit()
        }
        view.btnSuccursales.setOnClickListener {
            // Création d'une transaction qui sera une nouvelle instance du fragment de la liste des succursales et qui sera ajouté à la pile
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.contentFrame, SuccursaleListFragment.newInstance(1))
            transaction.addToBackStack("Logo")
            transaction.commit()
        }
        return view
    }

    companion object {
                fun newInstance(): LogoFragment {
            val fragment = LogoFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }

}// Il faut un constructeur vide qui soit publique.
