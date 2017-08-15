package com.roderick.steve.mapper7;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements InfoFragement.OnFragmentInteractionListener, ItemFragment.OnListFragmentInteractionListener {

    private static String url = "";
    private static String devAdd = "";
    final Context context = this;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.v("WEAVER_", "Location Change");
            //textView.setText(String.valueOf(updates) + " updates");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    ArrayList<HashMap<String, String>> contactList;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ProgressDialog pDialog;
    private ItemFragment itemFragment;
    private InfoFragement infoFragment;
    private List<Row> rowsToDisplay = new ArrayList<Row>();
    public static double latitude;
    public static double longitude;
    public static long lastGPSupdateV;

    private void handlePermissionsAndGetLocation() {


    }

    //Place this method into Activity. Did you working with onActivityResult? This method works same.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        if (requestCode == 1234) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goGps();
            } else {
                Toast.makeText(this,
                        "No permissions",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //This method also in Activity
    void goGps() {
        try {
            final Context ctx = this;

            final LocationManager locMan
                    = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            LocationListener locLis = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {


                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    lastGPSupdateV = System.currentTimeMillis();
//                    Toast.makeText(ctx,  + ", " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStatusChanged(String provider, int status,
                                            Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };

            locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10,
                    locLis, null);
        } catch (SecurityException ex) {
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        contactList = new ArrayList<>();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M /* If Android is 2.3 or newer */
                && checkSelfPermission(
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {    /* And if this is first call */
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1234);
        } else {
            goGps();
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoFragment.updateGPSText("Current GPS: " + round(latitude,4) + " , " + round(longitude,4));

                EditText edit = (EditText) findViewById(R.id.editText);
                url = edit.getText().toString();

                edit = (EditText) findViewById(R.id.editText3);
                devAdd = edit.getText().toString();


                Snackbar.make(view, "Syncing data from Things Connected..." + url, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new GetContacts().execute();


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Row item) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    infoFragment = InfoFragement.newInstance(0, "Page # 1");
                    return infoFragment;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    itemFragment = ItemFragment.newInstance(0, "Page # 1");
                    return itemFragment;
                case 2: // Fragment # 1 - This will show SecondFragment
                    return PlaceholderFragment.newInstance(position + 1);
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            Log.d("myTag", "preparing to contact url" + url);


            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            rowsToDisplay = new ArrayList<Row>();
            if (jsonStr != null) {
                try {
                    JSONArray framedata = new JSONArray(jsonStr);


                    rowsToDisplay.add(new Row("Time                     ", "Count", "Dist", "LSNR", "Rate"));
                    for (int i = 0; i < framedata.length(); i++) {
                        JSONObject c = framedata.getJSONObject(i);

                        String time = c.getString("time");
                        Date recDate = Helper.transformDate(time);
                        String timeFormat = Helper.returnDateFormatTime(recDate);
                        String counter = c.getString("counter");
                      //  String devAddReceived = c.getString("devAdd");
                        String devAddReceived = "07902b18";
                        Double gw_lat;
                        Double gw_long;
                        try {
                             gw_lat = Double.parseDouble(c.getString("lat"));
                             gw_long = Double.parseDouble(c.getString("long"));
                        } catch (Exception e) {
                             gw_lat = -1.0;
                             gw_long = -1.0;
                        }

                        String distance = "-1";

                        // if we have a recent GPS update then use this to calculate distance
                        long  frameTimestamp = recDate.getTime();


                        long difference = frameTimestamp - lastGPSupdateV;
                        // want abs value
                        if (difference < 0) {
                            difference *= -1;
                        }
                        //if (difference < 120 * 1000) {


                           distance = Double.toString( round(DistanceCalculator.calcDistance(latitude,longitude,gw_lat, gw_long,"K"),2));

                       // }
                        Log.d("myTag",recDate + "|" + new Date(lastGPSupdateV) + "|" + difference + "|" + distance);

                        String lsnr = c.getString("lsnr");
                        String datarate = c.getString("datarate").replace("SF", "").replace("BW", "|");


                        // filter other device addresses
                        if (devAddReceived.equalsIgnoreCase(devAdd)) {
                            rowsToDisplay.add(new Row(timeFormat, counter, distance, lsnr, datarate));
                        }

                      //  Collections.sort(rowsToDisplay);

                    }
                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if (rowsToDisplay.size() > 0) {
            } else {
                rowsToDisplay = new ArrayList<Row>();
                rowsToDisplay.add(new Row("", "", "", "", ""));
            }
            itemFragment.updateList(rowsToDisplay);
        }

    }



}
