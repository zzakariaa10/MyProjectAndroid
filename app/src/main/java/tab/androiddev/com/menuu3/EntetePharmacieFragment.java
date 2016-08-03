package tab.androiddev.com.menuu3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

/**
 * Created by Zakariaa on 27/07/2016.
 */
public class EntetePharmacieFragment extends android.support.v4.app.Fragment {

    int normal_bool =1;
    int garde_bool = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.entete_pharmacie_fragment_lyaout, container, false);
        final SearchView searchView = (SearchView) rootView.findViewById(R.id.searchview1);
        final Button normal = (Button) rootView.findViewById(R.id.button_normal);
        final Button garde = (Button) rootView.findViewById(R.id.button_garde);
        normal.setTextColor(Color.parseColor("#ff1d9c00"));
        garde.setTextColor(Color.parseColor("#ff1d9c00"));


        normal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if((normal_bool==0) || (garde_bool==0))
                     {
                      normal_bool = 1;
                      normal.setTextColor(Color.parseColor("#ff1d9c00"));
                     }
                else {
                      normal_bool = 0;
                      normal.setTextColor(Color.parseColor("#de6c6c6c"));
                     }
            }
        });

        garde.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((garde_bool == 0)|| (normal_bool==0)) {
                    garde_bool = 1;
                    garde.setTextColor(Color.parseColor("#ff1d9c00"));
                }
                else
                {
                    garde_bool=0;
                    garde.setTextColor(Color.parseColor("#de6c6c6c"));
                }


            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //  searchView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));

                return false;
            }
        });
        return rootView;
    }

}
