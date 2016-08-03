package tab.androiddev.com.menuu3;

import java.util.Date;

/**
 * Created by Zakariaa on 29/07/2016.
 */
public class Pharmacie {
    public String name;
    public String adresse;
    public String type_;
    public Date date_debut_garde;
    public Date date_fin_garde;

    Pharmacie(String name,String adresse,String type_)
    {
        this.name=name;
        this.adresse=adresse;
        this.type_=type_;
    }

    Pharmacie(String name,String adresse,String type_,Date date_debut_garde,Date date_fin_garde)
    {
        this.name=name;
        this.adresse=adresse;
        this.type_=type_;
        this.date_debut_garde=date_debut_garde;
        this.date_fin_garde=date_fin_garde;
    }
}
