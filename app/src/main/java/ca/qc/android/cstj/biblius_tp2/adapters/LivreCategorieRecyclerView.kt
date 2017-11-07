package ca.qc.android.cstj.biblius_tp2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.fragments.LivreListFragment
import ca.qc.android.cstj.biblius_tp2.helpers.TP1_WEB_SERVICES
import ca.qc.android.cstj.biblius_tp2.models.Livre
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_livre_categorie.view.*

/**
 * Created by Administrateur on 2017-11-07.
 */
class LivreCategorieRecyclerView(private val mValues:List<Livre>,
                                 private val mListener: LivreListFragment.OnListFragmentInteractionListener?):RecyclerView.Adapter<LivreCategorieRecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_livre_categorie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(mValues[position])
        holder.mView.setOnClickListener{
            mListener!!.onListCategorieFragmentInteraction(holder.livre)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder( val mView: View):RecyclerView.ViewHolder(mView) {
        var lblTitre = mView.lblTitre
        var livre: Livre? = null
        var lblAuteur = mView.lblAuteur
        var imgLivre = mView.imgLivre

        fun bind(livre: Livre){
            this.livre = livre
            this.lblTitre.text = livre.titre
            this.lblAuteur.text = livre.auteur

            val urlLivre = "$TP1_WEB_SERVICES${livre.image}"
            Picasso.with(imgLivre.context).load(urlLivre).centerCrop().fit().into(imgLivre)
        }
    }
}