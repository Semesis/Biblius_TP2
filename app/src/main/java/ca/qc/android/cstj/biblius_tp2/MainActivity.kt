package ca.qc.android.cstj.biblius_tp2

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ca.qc.android.cstj.biblius_tp2.fragments.*
import ca.qc.android.cstj.biblius_tp2.models.Categorie
import ca.qc.android.cstj.biblius_tp2.models.Item
import ca.qc.android.cstj.biblius_tp2.models.Livre
import ca.qc.android.cstj.biblius_tp2.models.Succursale
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(),
                     NavigationView.OnNavigationItemSelectedListener,
                     OnListItemFragmentInteractionListener, LivreListFragment.OnListFragmentInteractionListener {

    // Fonction qui permet d'afficher les détails d'un livre. On reçoit un livre en paramètre, qui est le livre sélectionné dans la liste.
    override fun onLivreCategorieFragmentInteraction(livre: Livre?) {
        val transaction = fragmentManager.beginTransaction()
        transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
        if (livre != null) {
            // Si le livre n'est pas nulll, on remplace le gragment par celui qui contient les détails du livre et on l'ajoute à la pile
            transaction.replace(R.id.contentFrame, LivreDetailFragment.newInstance(livre.href))
            transaction.addToBackStack("DetailLivre${livre.href}")
        }
        // Le commit permet d'exécuter la modification, donc d'afficher la transaction
        transaction.commit()
    }

    // Fonction qui permet d'ajouter à la fin de la pile la liste de livres ou les détails d'une succursale selon le type de l'item passé en passé en paramètre
    override fun onListItemFragmentInteraction(item: Item?) {
        Runnable {
            // On crée une transaction afin d'y ajouter le bon fragment à afficher
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            // On choisi le fragment selon le type de l'item, si c'est Succursale, on affiche les détails de la succursale sélectionnée, sinon on affiche la liste des livres pour la catégorie sélectionnée.
            when(item) {
                is Succursale -> {
                    // On remplace l'affichage et on ajoute le fragment à la fin de la pile.
                    transaction.replace(R.id.contentFrame, SuccursaleDetailsFragment.newInstance(item.href))
                    transaction.addToBackStack("DetailsSuccursale${item.href}")
                }
                is Categorie -> {
                    // On remplace l'affichage et on ajoute le fragment à la fin de la pile.
                    transaction.replace(R.id.contentFrame, LivreListFragment.newInstance(1, item.href))
                    transaction.addToBackStack("ListLivre${item.href}")
                }
            }
            // Le commit permet d'exécuter la modification, donc d'afficher la transaction
            transaction.commit()
        }.run()
    }

    // Ici. on crée on affiche le fragment contenant le logo qui nous sert de menu principal.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        // Création de la transaction
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, LogoFragment.newInstance())
        // Le commit permet d'exécuter la modification, donc d'afficher la transaction
        transaction.commit()
    }

    // Fait appel au bouton précédent
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        /* On veut affiché la bonne liste en fonction du type de l'item, deux choix sont possibles, afficher la liste des catégories ou afficher celle des succursales.
        *  Lorsque choisi, le fragment est ajouté à la pile et il est affiché
        */
        when (item.itemId) {
            R.id.nav_categorie -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack("Categories")
                transaction.replace(R.id.contentFrame, CategorieListFragment.newInstance(1))
                transaction.commit()
            }
            R.id.nav_succursale -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.addToBackStack("Succursales")
                transaction.replace(R.id.contentFrame, SuccursaleListFragment.newInstance(1))
                transaction.commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
