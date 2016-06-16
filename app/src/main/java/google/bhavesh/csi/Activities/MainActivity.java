package google.bhavesh.csi.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;

import google.bhavesh.csi.PojoData.TechFestDialog;
import google.bhavesh.csi.R;
import google.bhavesh.csi.fragments.FragmentAboutCSI;
import google.bhavesh.csi.fragments.FragmentCSIRAIT;
import google.bhavesh.csi.fragments.FragmentDeveloper;
import google.bhavesh.csi.fragments.FragmentGallery;
import google.bhavesh.csi.fragments.FragmentMagazines;
import google.bhavesh.csi.fragments.FragmentTechfest;
import google.bhavesh.csi.fragments.FragmentTechtalks;
import google.bhavesh.csi.fragments.FragmentWhats;
import google.bhavesh.csi.fragments.FragmentWorkshops;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    public FragmentManager mFragmentManager;
   public  FragmentTransaction fragmentTransaction;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  CollapsingToolbarLayout collapsingToolbar =
           //     (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
     //   collapsingToolbar.setTitle("CSI");




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Feedback form goes here!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */
fragnmenttrans();


    }

                @Override
                public boolean onCreateOptionsMenu (Menu menu){
                    // Inflate the menu; this adds items to the action bar if it is present.
                    getMenuInflater().inflate(R.menu.menu_main, menu);
                    return true;
                }

                @Override
                public boolean onOptionsItemSelected (MenuItem item){
                    // Handle action bar item clicks here. The action bar will
                    // automatically handle clicks on the Home/Up button, so long
                    // as you specify a parent activity in AndroidManifest.xml.
                    int id = item.getItemId();

                    //noinspection SimplifiableIfStatement
                    if (id == R.id.aboutcsi) {

                            toolbar.setTitle(" CSI ");
                            //FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.containerView,new FragmentAboutCSI());
                        }

                    if (id == R.id.csirait) {

                        toolbar.setTitle("CSI RAIT");
                     //   FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.containerView,new FragmentCSIRAIT());
                    }
                    if (id == R.id.developer) {
                        toolbar.setTitle("Developer");

             //FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.containerView,new FragmentDeveloper());
                    }

                    return super.onOptionsItemSelected(item);
                }

    public void fragnmenttrans()
    {


        mFragmentManager = getSupportFragmentManager();
        fragmentTransaction = mFragmentManager.beginTransaction();
        toolbar.setTitle("CSI");
        fragmentTransaction.replace(R.id.containerView,new FragmentAboutCSI()).commit();
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.techfests) {
                    toolbar.setTitle("TechFests");
                    fragmentTransaction.replace(R.id.containerView, new FragmentTechfest());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();


                }

                if (menuItem.getItemId() == R.id.workshops) {
                    toolbar.setTitle("Workshops");
                    fragmentTransaction.replace(R.id.containerView,new FragmentWorkshops());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();

                }


                if (menuItem.getItemId() == R.id.techtalks) {
                    toolbar.setTitle("TechTalks");
                    fragmentTransaction.replace(R.id.containerView,new FragmentTechtalks());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();


                }
                if (menuItem.getItemId() == R.id.magazines) {
                    toolbar.setTitle("Magazines");
                    fragmentTransaction.replace(R.id.containerView,new FragmentMagazines());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    //WebView webview=new WebView(MainActivity.this);

                }

                if (menuItem.getItemId() == R.id.whatshot) {
                    toolbar.setTitle("Hot");
                    fragmentTransaction.replace(R.id.containerView,new FragmentWhats());
                    fragmentTransaction.addToBackStack("");
                    fragmentTransaction.commit();
                    //WebView webview=new WebView(MainActivity.this);

                }



//
              return true;


            }


        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

            }
