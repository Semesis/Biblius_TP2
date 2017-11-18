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

// Le mValues représente la liste des livres d'une catégorie.
class LivreCategorieRecyclerViewAdapter(private val mValues:List<Livre>,
                                        private val mListener: LivreListFragment.OnListFragmentInteractionListener?):RecyclerView.Adapter<LivreCategorieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_livre_categorie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // On veut binder les items du ViewHolder
        holder.bind(mValues[position])
        // On met une fonction sur chaque item de la liste afin de pouvoir cliquer pour accéder aux inforamations des livres, donc les détails du livre sélectionné
        holder.mView.setOnClickListener{
            mListener!!.onLivreCategorieFragmentInteraction(holder.livre)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    // Ici, on veut créer des variables qui nous permettront de binder les informations dans la carte des livres d'une catégorie.
    inner class ViewHolder( val mView: View):RecyclerView.ViewHolder(mView) {
        var lblTitre = mView.lblTitre
        var livre: Livre? = null
        var lblAuteur = mView.lblAuteur
        var imgLivre = mView.imgLivre

        // Prend un livre en commentaire et fait le binding entre les items de la view et les informations voulues.
        fun bind(livre: Livre){
            this.livre = livre
            this.lblTitre.text = livre.titre
            this.lblAuteur.text = livre.auteur

            // On doit créer une url pour aller chercher l'image d'un livre dans la base de données.
            val urlLivre = "$TP1_WEB_SERVICES${livre.image}"
            Picasso.with(imgLivre.context)
                    .load(urlLivre)
                    .resize(400,500)
                    .into(imgLivre)
        }
    }
}