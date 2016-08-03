package tab.androiddev.com.menuu3;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;

/**
 * Created by Zakariaa on 27/07/2016.
 */
public class PharmacieFragment extends Fragment{
    //Filtre Garde / Normal
    public Boolean gardeBoolean = true;
    public Boolean normalBoolean = true;
    public static Pharmacie pharmacieClicked;
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
        View rootView = inflater.inflate(R.layout.pharmacie_fragment_layout, container, false);
        //Font
         Font font=new Font();
        ((TextView)rootView.findViewById(R.id.button_normal)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
        ((TextView)rootView.findViewById(R.id.button_garde)).setTypeface(font.get_icons("fonts/ionicons.ttf", getActivity()));


        final ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();

        pharmacies.add(new Pharmacie("La grande Pharmacie de Salé","2, bd Onze Janvier , 11150 SALE","normal"));
        pharmacies.add(new Pharmacie("Ouest Répartition Pharmaceutique","1374, rue Laâyoune, z.i. hay Errahma, 11070 SALE","normal"));
        pharmacies.add(new Pharmacie("Paradif","16, bd Mohammed V, résid. AlMostakbale, appt. 1, 11000 SALE","normal"));
        pharmacies.add(new Pharmacie("Paramek","27, bd Six Novembre, Tabriquet, 11000 SALE","normal"));
        pharmacies.add(new Pharmacie("Pharmacie 20 Aôut","Hay Tabriquet, résid. 20 Aôut, imm. B 1, mag. 2, 11000 SALE","garde"));


             final MyCustomAdapter myCustomAdapter = new MyCustomAdapter(pharmacies);
             ListView lv = (ListView) rootView.findViewById(R.id.listView);
             lv.setAdapter(myCustomAdapter);
             final SearchView searchView = (SearchView) rootView.findViewById(R.id.searchview1);
             searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

                                               {
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

//Filtre normal / garde par defaut activé
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
                if(gardeBoolean == true && normalBoolean==true )
                {
                    gardeBoolean=false;
                    button_garde.setTextColor(Color.parseColor("#de6c6c6c"));
                }
                else
                {
                    gardeBoolean=true;
                    button_garde.setTextColor(Color.parseColor("#ff1d9c00"));
                }
                myCustomAdapter.getFilter().filter(searchView.getQuery().toString());
            }
        });

        //onClickItem
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt = (TextView) view.findViewById(R.id.name);
                Toast.makeText(getActivity().getApplicationContext(), txt.getText(), Toast.LENGTH_LONG).show();
                pharmacieClicked = findPharmacieByName(txt.getText().toString(),pharmacies);


                PharmacieDetaille fragment2 = new PharmacieDetaille();
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.add(R.id.container2, fragment2, "Hopitals Location");
                fragmentTransaction2.commit();
            }
        });

        return rootView;
         }


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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view1=layoutInflater.inflate(R.layout.pharmacie_element, null);
            //Font
            Font font=new Font();

            ((TextView)view1.findViewById(R.id.fleche)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));
            ((TextView)view1.findViewById(R.id.garde_normal)).setTypeface(font.get_icons("fonts/ionicons.ttf",getActivity()));

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
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_);
                                filter.add(p);
                            }
                        }
                        if(gardeBoolean==true)
                        {
                            CharSequence str="GARDE";
                            if((filterlist.get(i).name.toUpperCase().contains(constraint))
                                    && (filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_);
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
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_);
                                filter.add(p);
                            }
                        }
                        if(gardeBoolean==true)
                        {
                            CharSequence str="GARDE";
                            if((filterlist.get(i).type_.toUpperCase().contains(str)))
                            {
                                Pharmacie p = new Pharmacie(filterlist.get(i).name,filterlist.get(i).adresse,filterlist.get(i).type_);
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
