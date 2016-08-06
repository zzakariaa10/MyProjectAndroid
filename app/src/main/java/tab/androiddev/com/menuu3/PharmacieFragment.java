package tab.androiddev.com.menuu3;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import tab.androiddev.com.menuu3.Modules.DirectionFinderListener;
import tab.androiddev.com.menuu3.Modules.Distance2Points;
import tab.androiddev.com.menuu3.Modules.JSONDisTime;
import tab.androiddev.com.menuu3.Modules.Route;

/**
 * Created by Zakariaa on 27/07/2016.
 */
public class PharmacieFragment extends Fragment implements  DirectionFinderListener {
    //Filtre Garde / Normal
    public Boolean gardeBoolean = true;
    public Boolean normalBoolean = true;
    public static Pharmacie pharmacieClicked;
    MyCustomAdapter myCustomAdapter;
    ListView lv;
    ArrayList<Pharmacie> pharmacies;
    String coordonnee;

    public Pharmacie findPharmacieByName(String name , ArrayList<Pharmacie> p)
    {
        Pharmacie pharma = null;
        int i;
        for(i=0;i<p.size();i++)
        {
            if(p.get(i).name.equals(name))
            {
                pharma=p.get(i);
            }
        }
        return pharma;
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pharmacie_fragment_layout, container, false);

        progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
                "Finding direction..!", true);

        //Font Icon-----------------------------------------------------------------------------------------------
         Font font=new Font();
        ((TextView)rootView.findViewById(R.id.button_normal)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
        ((TextView)rootView.findViewById(R.id.button_garde)).setTypeface(font.get_icons("fonts/ionicons.ttf", getActivity()));
        //---------------------------------------------------------------------------------------------------------
        pharmacies = new ArrayList<Pharmacie>();

       // Geocoder geocoder = new Geocoder(getActivity());
       // Distance2Points distance2Points = new Distance2Points();
       // double dst = distance2Points.distance2adresses(geocoder, "safi", "rabat");
        //Toast.makeText(getActivity().getApplication()," dd " + Double.toString(dst),Toast.LENGTH_SHORT).show();


        final SearchView searchView = (SearchView) rootView.findViewById(R.id.searchview1);

             lv = (ListView) rootView.findViewById(R.id.listView);
//onClick Item listView--------------------------------------------------------------------------

lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView txt_name = (TextView) rootView.findViewById(R.id.name);
        Toast.makeText(getActivity().getApplicationContext(),txt_name.getText(),Toast.LENGTH_SHORT).show();
    }
});
//end onClick Item listView--------------------------------------------------------------------------


        //Filtre-------------------------------------------------------------------------------------------------
             searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                                   @Override
                                                   public boolean onQueryTextSubmit(String query) {
                                                       return false;
                                                   }

                                                   @Override
                                                   public boolean onQueryTextChange(String query) {
                                                       myCustomAdapter.getFilter().filter(query);
                                                       return false;
                                                   }
                                               }
             );

//Filtre normal / garde par defaut active
        final Button button_normal = (Button) rootView.findViewById(R.id.button_normal);
        final Button button_garde = (Button) rootView.findViewById(R.id.button_garde);
        button_normal.setTextColor(Color.parseColor("#ff1d9c00"));
        button_garde.setTextColor(Color.parseColor("#ff1d9c00"));

        button_normal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (normalBoolean == false || gardeBoolean == false) {
                    normalBoolean = true;
                    button_normal.setTextColor(Color.parseColor("#ff1d9c00"));
                } else {
                    normalBoolean = false;
                    button_normal.setTextColor(Color.parseColor("#de6c6c6c"));
                }
                myCustomAdapter.getFilter().filter(searchView.getQuery().toString());
            }
        });


        button_garde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (gardeBoolean == true && normalBoolean == true) {
                    gardeBoolean = false;
                    button_garde.setTextColor(Color.parseColor("#de6c6c6c"));
                } else {
                    gardeBoolean = true;
                    button_garde.setTextColor(Color.parseColor("#ff1d9c00"));
                }
                myCustomAdapter.getFilter().filter(searchView.getQuery().toString());
            }
        });

      //End Filtre---------------------------------------------------------------------------------------------------------------
//--------------------Ma position------------------------
        GpsTracker gps = new GpsTracker(getActivity());
        double longitude=0;
        double latitude=0;
        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();
            // Toast.makeText(getActivity().getApplicationContext(),Double.toString(latitude) + "," + Double.toString(longitude),Toast.LENGTH_SHORT).show();
        }
//------End Ma position---------------------------------------------------

//----------------Add Pharmacie to arraylist + sendRequest with actual position and position of current pharmacie-----------------------------------------
         coordonnee = Double.toString(latitude)+","+Double.toString(longitude);
        Geocoder geocoder = new Geocoder(getActivity());
        Distance2Points distance2Points = new Distance2Points();
        //---------------------------------------------------------------------------------------------

//***********************************Add pharmacie for test *****************************************************************
        

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));

        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //sendRequest(coordonnee.toString(), "CASABLANCA");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));
        //sendRequest(coordonnee.toString(), "SALE");

        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "TAZA", "normal"));
        try {
            pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", URLEncoder.encode("safi maroc", "utf-8"), "normal"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        pharmacies.add(new Pharmacie("La grande Pharmacie de Sale", "RABAT", "normal"));

        //*********************************************************************************************************

//Recuperer les donnee jSon (API)

        //Destination en groupe--------------------------------
        String destinations="" ;

        for(int i=0;i<pharmacies.size();i++)
        {
            if(i<pharmacies.size()-1)
            destinations+=pharmacies.get(i).adresse + "|";
            else
                destinations+=(pharmacies.get(i).adresse);

        }
Toast.makeText(getActivity().getApplicationContext(),destinations,Toast.LENGTH_SHORT).show();
        //--------------------------------------------------------
        sendRequest(coordonnee.toString(), destinations.toString());



        myCustomAdapter = new MyCustomAdapter(pharmacies);
        lv.setAdapter(myCustomAdapter);


//----------------End Add Pharmacie-----------------------------------------------------------------------------------


//        JSONParser jsonParser= new JSONParser();





        return rootView;
         }

//****************Calcule Distance Via API (Required (implements DirectionFinderListener))*********************


    private ProgressDialog progressDialog;
    public void sendRequest(String origin, String destination) {

        try {
            new JSONDisTime(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
    }
    int j=0;
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
      //progressDialog.dismiss();
       int tr=0;
        for (Route route : routes) {
            pharmacies.get(j).distance=route.distance.text;
            pharmacies.get(j).timing=route.duration.text;

            // String str = Double.toString(dst.distance2adresses("rabat", "safi"));
        //    pharmacies.get(j).distance=Double.toString(dst);
            j++;
        }


            myCustomAdapter = new MyCustomAdapter(pharmacies);
            lv.setAdapter(myCustomAdapter);


        progressDialog.dismiss();


    }



    //****************************************************************************************

    public class MyCustomAdapter extends BaseAdapter implements Filterable {

        ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();
        CustomFilter filter;
        ArrayList<Pharmacie> filterlist;

        MyCustomAdapter(ArrayList<Pharmacie> pharmacies){

            this.pharmacies=pharmacies;
            this.filterlist=pharmacies;
                }


        public int getCount() {
            return pharmacies.size();
        }

        public Object getItem(int arg0) {
            return pharmacies.get(arg0).name;
        }

        public long getItemId(int position) {
            return position;
        }
//------------------------- for Element of View
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.pharmacie_element, null);
            //Font
            Font font=new Font();

            ((TextView)view1.findViewById(R.id.fleche)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
            ((TextView)view1.findViewById(R.id.garde_normal)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
            ((TextView)view1.findViewById(R.id.timing_icon)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
            ((TextView)view1.findViewById(R.id.distance_icon)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));

            //distance + timing
            TextView timing_icon =(TextView)view1.findViewById(R.id.timing_icon);
            TextView timing =(TextView)view1.findViewById(R.id.timing);
            TextView distance_icon =(TextView)view1.findViewById(R.id.distance_icon);
            TextView distt =(TextView)view1.findViewById(R.id.distance);

            timing.setText(pharmacies.get(position).timing);
            distt.setText(pharmacies.get(position).distance);

            TextView garde_normal =(TextView)view1.findViewById(R.id.garde_normal);
            TextView lv_name =(TextView)view1.findViewById(R.id.name);
            TextView lv_adress =(TextView)view1.findViewById(R.id.adress);
            lv_name.setText(pharmacies.get(position).name);
            lv_adress.setText(pharmacies.get(position).adresse);





            if(pharmacies.get(position).type_=="normal")
            {  garde_normal.setText(R.string.icon_normal);}
            else
            {  garde_normal.setText(R.string.icon_garde);}



            return view1;
        }

        @Override
        public Filter getFilter() {
            if(filter==null)
            {
                filter = new CustomFilter();

            }
            return filter;
        }
        public class CustomFilter extends Filter{
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults fr=new FilterResults();
                if(constraint.length()!=0 && constraint != null)
                {
                    constraint=constraint.toString().toUpperCase();
                    ArrayList<Pharmacie> filter= new ArrayList<Pharmacie>();
                    int i;
                    for(i=0;i<filterlist.size()-0;i++)
                    {
                        if(normalBoolean==true)
                        {
                            CharSequence str="NORMAL";
                            if((filterlist.get(i).name.toUpperCase().contains(constraint))
                                    && (filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_,filterlist.get(i).distance,filterlist.get(i).timing,filterlist.get(i).trie);
                                filter.add(p);
                            }
                        }
                        if(gardeBoolean==true)
                        {
                            CharSequence str="GARDE";
                            if((filterlist.get(i).name.toUpperCase().contains(constraint))
                                    && (filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_,filterlist.get(i).distance,filterlist.get(i).timing,filterlist.get(i).trie);
                                filter.add(p);
                            }
                        }
                    }
                    fr.count=filter.size();
                    fr.values=filter;
                }
                else
                {

                    constraint=constraint.toString().toUpperCase();
                    ArrayList<Pharmacie> filter= new ArrayList<Pharmacie>();
                    int i;
                    for(i=0;i<filterlist.size()-0;i++)
                    {
                        if(normalBoolean==true)
                        {
                            CharSequence str="NORMAL";
                            if((filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_,filterlist.get(i).distance,filterlist.get(i).timing,filterlist.get(i).trie);
                                filter.add(p);
                            }
                        }
                        if(gardeBoolean==true)
                        {
                            CharSequence str="GARDE";
                            if((filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_,filterlist.get(i).distance,filterlist.get(i).timing,filterlist.get(i).trie);
                                filter.add(p);
                            }
                        }
                    }
                    fr.count=filter.size();
                    fr.values=filter;
                }

                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
            pharmacies = (ArrayList<Pharmacie>)results.values;
                notifyDataSetChanged();

            }
        }
    }
}
