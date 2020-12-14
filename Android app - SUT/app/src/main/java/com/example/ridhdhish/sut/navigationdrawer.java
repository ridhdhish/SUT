package com.example.ridhdhish.sut;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static maes.tech.intentanim.CustomIntent.customType;


public class navigationdrawer extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RelativeLayout appBarLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Fragment fragment;
    FragmentManager fragmentManager;
    TextView txtNavigationDrawerName;
    TextView txtNavigationDrawerEmailid;
    ImageView imgNavigationDrawer;

    SharedPreferences sharedPreferences;

    private static String METHOD_NAME = "Users_Profile_Select";
    private static String NAMESPACE = "";
    private static String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    private static String url = "";
    private static String imgUrl = "";
          String img="siteimages/profilePictures/";
    String src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigationdrawer);

        NAMESPACE = getString(R.string.NAMESPACE);
        imgUrl = getString(R.string.WEBSERVER_IP) + img;
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        url = getResources().getString(R.string.url);

        sharedPreferences = getSharedPreferences("Pref", Context.MODE_PRIVATE);

        TypefaceUtil.overrideFont(this, "SERIF", "fonts/Product_Sans_Regular.ttf");

        /*Calligrapher c = new Calligrapher(this);
        c.setFont(this, "Product_Sans_Regular.ttf", true);*/

        setTitle("");
        fragmentManager = getSupportFragmentManager();
        /* Setup Navigation Drawer */
        try {

            toolbar = (Toolbar) findViewById(R.id.appbarToolBar);
            setSupportActionBar(toolbar);

          /*  getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
            ActionBar actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#550000ff")));*/

            drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
            appBarLayout = (RelativeLayout) findViewById(R.id.appBarLayout);
            navigationView = (NavigationView) findViewById(R.id.navigationView);

            toggle = new ActionBarDrawerToggle(navigationdrawer.this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            };

            drawerLayout.setDrawerListener(toggle);
            toggle.syncState();


            View headerView = navigationView.getHeaderView(0);

            txtNavigationDrawerName = (TextView) headerView.findViewById(R.id.txtNavigationDrawerName);
            txtNavigationDrawerEmailid = (TextView) headerView.findViewById(R.id.txtNavigationDrawerEmailid);
            imgNavigationDrawer = (ImageView) headerView.findViewById(R.id.imgNavigationDrawer);

            txtNavigationDrawerName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(navigationdrawer.this, profile.class);
                    startActivity(i);
                }
            });

            txtNavigationDrawerEmailid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(navigationdrawer.this, profile.class);
                    startActivity(i);
                }
            });

            imgNavigationDrawer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(navigationdrawer.this, profile.class);
                    startActivity(i);
                }
            });


            getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                @Override
                public void onBackStackChanged() {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                        toggle.setDrawerIndicatorEnabled(true);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        getSupportActionBar().setDisplayShowHomeEnabled(false);
                        toggle.syncState();
                    } else {

                        toggle.setDrawerIndicatorEnabled(false);
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                    }
                }
            });


        } catch (Exception ex) {
            Toast.makeText(navigationdrawer.this, ex.toString(), Toast.LENGTH_LONG).show();
        }

        /* Setting home fragment to open when application run first time */
        HomeFragment();

        navigationView.getMenu().getItem(0).setChecked(true);

        /* Setting menu click event of Navigation Drawer */
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                fragment = null;
                Intent i;
                switch (item.getItemId()) {
//                    case R.id.menuHome:
//                        fragment = new home();
//                        break;
                    case R.id.menuAnnouncement:
                        //  fragment = new complaint();
                        i = new Intent(navigationdrawer.this, announcement.class);
                        startActivity(i);
                        customType(navigationdrawer.this,"left-to-right");
                        break;
                    case R.id.menuMeeting:
                        i = new Intent(navigationdrawer.this, meeting.class);
                        startActivity(i);
                        customType(navigationdrawer.this,"fadein-to-fadeout");
                        break;
                    case R.id.menuService:
                        i = new Intent(navigationdrawer.this, service.class);
                        startActivity(i);
                        customType(navigationdrawer.this,"fadein-to-fadeout");
                        break;
                    case R.id.menuComplaints:
                        i = new Intent(navigationdrawer.this, complaint.class);
                        startActivity(i);
                        customType(navigationdrawer.this,"fadein-to-fadeout");
                        break;
                    case R.id.menuFlatHolder:
                        i = new Intent(navigationdrawer.this, user.class);
                        startActivity(i);
                        break;
                    case R.id.menuMaintenance:
                        i = new Intent(navigationdrawer.this, maintenance.class);
                        startActivity(i);
                        customType(navigationdrawer.this,"fadein-to-fadeout");
                        break;
                    case R.id.menuSettings:
                        i = new Intent(navigationdrawer.this, setting.class);
                        customType(navigationdrawer.this,"fadein-to-fadeout");
                        startActivity(i);
                        break;
                    default:
                       // Toast.makeText(navigationdrawer.this, "No Data", Toast.LENGTH_LONG).show();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                setFragmentManager(true);

                return true;
            }
        });

        new AsyncNavigation().execute(sharedPreferences.getString("Userid", null).toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncNavigation().execute(sharedPreferences.getString("Userid", null).toString());
    }

    public class AsyncNavigation extends AsyncTask<String, Void, SoapObject> {
        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject documentElement = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo pi = new PropertyInfo();
            pi.setName("userId");
            pi.setValue(params[0]);
            pi.setType(String.class);
            request.addProperty(pi);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);
            try {
                HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject diffgram = (SoapObject) response.getProperty("diffgram");

                if (diffgram.getPropertyCount() > 0) {
                    documentElement = (SoapObject) diffgram.getProperty("DocumentElement");
                }
            } catch (Exception E) {
                Log.e("Error-->> ", E.toString());
            }

            return documentElement;
        }

        @Override
        protected void onPostExecute(SoapObject resultXML) {
            super.onPostExecute(resultXML);
            afterAsyncNavigation(resultXML);
        }
    }

    public void afterAsyncNavigation(SoapObject resultXML) {

        //Toast.makeText(this, resultXML.toString(), Toast.LENGTH_LONG).show();
        try {
            String KEY_ITEM = "Users";
            String Emailid = "UserEmailid";
            String Name = "UserName";
            SoapObject table = null;

            try {

                if (resultXML.getPropertyCount() > 0) {
                    table = (SoapObject) resultXML.getProperty(KEY_ITEM);
                    String e = table.getProperty(Emailid).toString();
                    String n = table.getProperty(Name).toString();
                    src = table.getProperty("UserProfilePicture").toString();
                    txtNavigationDrawerEmailid.setText(e);
                    txtNavigationDrawerName.setText(n);

                    Log.e("Siteimage", imgUrl + src);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            final Bitmap b = getBitmapFromURL(imgUrl + src);
                            imgNavigationDrawer.post(new Runnable() {
                                @Override
                                public void run() {
                                    imgNavigationDrawer.setImageBitmap(b);

                                }
                            });

                        }
                    }).start();
                }
            }
            catch (Exception e){

            }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Back pressed", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);

        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    /* Function to set first fragment */
    public void HomeFragment() {
        fragment = new home();
        setFragmentManager(false);
    }

    /* Function to replace LinearLayout available in content.xml file with fragment */
    public void setFragmentManager(boolean addToBackStack) {
        if (fragment != null) {

            fragmentManager = getSupportFragmentManager();

            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.replacableLayout, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack) ft.addToBackStack(null);
            ft.commit();

        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
