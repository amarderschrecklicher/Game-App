package ba.etf.rma23.projekat

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ba.etf.rma23.projekat.GameDetailsFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity(), DataListener {

    private lateinit var menu1: MenuItem
    private lateinit var menu2: MenuItem
    lateinit var navHostFragment: NavHostFragment
    lateinit  var navController : NavController
    lateinit var navView : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navController = navHostFragment.navController
            if(navController.currentDestination!!.id == R.id.gameDetailsItem)
                navController.popBackStack()

            navView = findViewById(R.id.bottom_nav)
            navView.setupWithNavController(navController)
            menu1 = navView.menu.findItem(R.id.homeItem)
            menu2 = navView.menu.findItem(R.id.gameDetailsItem)
            menu2.isEnabled = false
            menu1.isEnabled = false
            navView.isEnabled = false

            navController.addOnDestinationChangedListener { _, destination, _ ->
                navView.isEnabled = true
                if (destination.id == R.id.homeItem) {
                    menu1.isEnabled = false
                    if (HomeFragment.gameToShowDetails != null)
                        menu2.isEnabled = true
                } else if (destination.id == R.id.gameDetailsItem) {
                    menu1.isEnabled = true
                    menu2.isEnabled = false

                }
            }
        }

    }

    override fun refreshDetails(item: Game) {
        val details = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_land) as NavHostFragment
        val destination = GameDetailsFragmentDirections.test()
        details.navController.navigate(destination)
    }

    override fun showDetails() {
        menu2.isEnabled = true
        navView.selectedItemId = menu2.itemId
        navView.performClick()
    }


}
