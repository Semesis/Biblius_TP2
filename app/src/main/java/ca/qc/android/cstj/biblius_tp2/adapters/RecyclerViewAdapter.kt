package ca.qc.android.cstj.biblius_tp2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ca.qc.android.cstj.biblius_tp2.R

import ca.qc.android.cstj.biblius_tp2.fragments.OnListItemFragmentInteractionListener
import ca.qc.android.cstj.biblius_tp2.models.Item
import kotlinx.android.synthetic.main.card_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Item] and makes a call to the
 * specified [OnListItemFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
 class RecyclerViewAdapter(private val mValues:List<Item>, private val mListener: OnListItemFragmentInteractionListener?):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

     override fun onCreateViewHolder(parent:ViewGroup, viewType:Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

     override fun onBindViewHolder(holder: ViewHolder, position:Int) {
        holder.bind(mValues[position])
       /* holder.mIdView.setText(mValues.get(position).id)
        holder.mContentView.setText(mValues.get(position).content)*/

        holder.mView.setOnClickListener {


                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener!!.onListItemFragmentInteraction(holder.item)


        }
    }

     override fun getItemCount():Int {
        return mValues.size
    }

    inner class ViewHolder( val mView:View):RecyclerView.ViewHolder(mView) {
        var lblNomItem = mView.lblNomItem
        var item: Item? = null

        fun bind(item: Item){
            this.item = item
            this.lblNomItem.text = item.getAffichage()
        }

    }
}
