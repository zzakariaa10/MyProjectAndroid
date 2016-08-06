package tab.androiddev.com.menuu3;

/**
 * Created by Zakariaa on 29/07/2016.
 */
public class Pharmacie {
    public String name;
    public String adresse;
    public String type_;
    public String timing;
    public String distance ;
    public int trie;

    Pharmacie(String name,String adresse,String type_)
    {
        this.name=name;
        this.adresse=adresse;
        this.type_=type_;
    }

    Pharmacie(String name,String adresse,String type_,String distance,String timing,int trie)
    {
        this.name=name;
        this.adresse=adresse;
        this.type_=type_;
        this.distance=distance;
        this.timing=timing;
        this.trie=trie;

    }
}
