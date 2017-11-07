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
import ca.qc.android.cstj.biblius_tp2.fragments.CategorieListFragment
import ca.qc.android.cstj.biblius_tp2.fragments.LivreListFragment
import ca.qc.android.cstj.biblius_tp2.fragments.OnListItemFragmentInteractionListener
import ca.qc.android.cstj.biblius_tp2.fragments.SuccursaleListFragment
import ca.qc.android.cstj.biblius_tp2.models.Categorie
import ca.qc.android.cstj.biblius_tp2.models.Item
import ca.qc.android.cstj.biblius_tp2.models.Succursale
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(),
                     NavigationView.OnNavigationItemSelectedListener,
                     OnListItemFragmentInteractionListener {

    override fun onListItemFragmentInteraction(item: Item?) {
        Runnable {
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            when (item) {
                is Succursale -> {
                    /*transaction.replace(R.id.contentFrame, SuccursaleDetailsFragment.newInstance(item.href))
                    transaction.addToBackStack("DetailsSuccursale${item.href}")*/
                }
                is Categorie -> {
                    transaction.replace(R.id.contentFrame, LivreListFragment.newInstance(1, item.href))
                    transaction.addToBackStack("DetailsLivre${item.href}")
                }
            }
            transaction.commit()
        }.run()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val transaction = fragmentManager.beginTransaction()

        transaction.replace(R.id.contentFrame, CategorieListFragment.newInstance(1))
        transaction.commit()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }*/

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_categorie -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_categorie -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.contentFrame, CategorieListFragment.newInstance(1))
                transaction.commit()
            }
            R.id.nav_succursale -> {
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.contentFrame, SuccursaleListFragment.newInstance(1))
                transaction.commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
