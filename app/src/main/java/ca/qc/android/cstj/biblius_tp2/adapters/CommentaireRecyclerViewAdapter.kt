package ca.qc.android.cstj.biblius_tp2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ca.qc.android.cstj.biblius_tp2.R
import ca.qc.android.cstj.biblius_tp2.models.Commentaire
import kotlinx.android.synthetic.main.card_commentaire.view.*


/**
 * Created by Administrateur on 2017-11-14.
 */
class CommentaireRecyclerViewAdapter (private val mValues:List<Commentaire>):RecyclerView.Adapter<CommentaireRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_commentaire, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(mValues[position])

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder( val mView: View):RecyclerView.ViewHolder(mView) {
        var lblAuteurCommentaire = mView.lblAuteurCommentaire
        var commentaire: Commentaire? = null
        var lblCommentaire = mView.lblCommentaire
        var lblDateCommentaire = mView.lblDateCommentaire
        var rtbEtoileCommentaire = mView.rtbEtoileCommentaire

        fun bind(commentaire: Commentaire){
            this.commentaire = commentaire
            this.lblAuteurCommentaire.text = commentaire.auteurCommentaire
            this.lblCommentaire.text = commentaire.message
            this.lblDateCommentaire.text = "${commentaire.dateCommentaire.substring(0,10)}"
            this.rtbEtoileCommentaire.rating = commentaire.etoile.toFloat()
        }

    }
}