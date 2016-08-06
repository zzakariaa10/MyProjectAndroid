package tab.androiddev.com.menuu3;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import tab.androiddev.com.menuu3.Modules.DirectionFinder;
import tab.androiddev.com.menuu3.Modules.DirectionFinderListener;
import tab.androiddev.com.menuu3.Modules.Route;

/**
 * Created by Zakariaa on 31/07/2016.
 */
public class PharmacieDetaille extends Fragment  implements  DirectionFinderListener {


    private GoogleMap mMap;
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            try {

                rootView = inflater.inflate(R.layout.pharmaciedetaille,container,false);
                TextView name=(TextView) rootView.findViewById(R.id.name);
                TextView adr=(TextView) rootView.findViewById(R.id.adresse);
                name.setText(PharmacieFragment.pharmacieClicked.name);
                adr.setText(PharmacieFragment.pharmacieClicked.adresse);

                ((TextView)rootView.findViewById(R.id.adr)).setTypeface(Font.get_icons("fonts/ionicons.ttf", getActivity()));
                ((TextView)rootView.findViewById(R.id.dist1)).setTypeface(Font.get_icons("fonts/ionicons.ttf", getActivity()));
                ((TextView)rootView.findViewById(R.id.dist4)).setTypeface(Font.get_icons("fonts/ionicons.ttf", getActivity()));
                Button b =(Button) rootView.findViewById(R.id.button);

                sendRequest();

                TextView rv = (TextView)rootView.findViewById(R.id.dist2);
                TextView rv2 = (TextView)rootView.findViewById(R.id.dist3);
                TextView rv3 = (TextView)rootView.findViewById(R.id.textView);

                Geocoder geocoder = new Geocoder(getActivity());

                double dst = proximity(geocoder,"safi","rabat");

                rv3.setText(Double.toString(dst));



            } catch (Exception e) {
                System.out.println("error");
            }
        return rootView;
            }
    private void sendRequest() {
        try {
            new DirectionFinder(this, PharmacieFragment.pharmacieClicked.adresse, "rabat").execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDirectionFinderStart() {
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {

    }
    //Distance entre 2 points
    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


public double proximity(Geocoder geocoder,String adresse1,String adresse2)
{

    List<Address> addresses = null;
    List<Address> addresses1 = null;
    try {
        addresses = geocoder.getFromLocationName(adresse1, 1);
    } catch (IOException e) {
        e.printStackTrace();
    }

    double latitude= 0;
    double longitude= 0;

    if(addresses.size() > 0) {
        latitude= addresses.get(0).getLatitude();
        longitude= addresses.get(0).getLongitude();
    }

    try {
        addresses1 = geocoder.getFromLocationName(adresse2, 1);
    } catch (IOException e) {
        e.printStackTrace();
    }

    double latitude1= 0;
    double longitude1= 0;

    if(addresses1.size() > 0) {
        latitude1= addresses1.get(0).getLatitude();
        longitude1= addresses1.get(0).getLongitude();
    }

    double dst = 0;
    dst= distance(latitude,longitude,latitude1,longitude1,'K');
    return dst;
}


}
