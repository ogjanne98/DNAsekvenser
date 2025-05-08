import java.util.*;
import java.io.*;

/** Klasse med beholder av HashMap<String,Subsekvens>.
 * Metoder for å skape slike HashMaps fra datafiler og for å slå
 * sammen HashMaps. */
public class SubsekvensRegister
{
    private ArrayList<HashMap<String,Subsekvens>> register;
    private static int sekvensLengde = 3;

    public SubsekvensRegister()
    {
        this.register = new ArrayList<HashMap<String,Subsekvens>>();
    }

    public void leggTil(HashMap<String,Subsekvens> map){register.add(map);}

    public HashMap<String,Subsekvens> hente(int indeks){return register.remove(indeks);}

    public int antall(){return register.size();}

    /** Leser in en fil med DNA-sekvenser og skaper en HashMap, for hver unik 
     * subsekvens-streng av lengde 'this.sekvensLengde' legger til et Subsekvens-objekt
     * i HashMapen med tilhørende subsekvens-streng som nykkel.
     * @param filnavn filen som skal leses in
     * @return HashMapen
    */
    public static HashMap<String,Subsekvens> lesImmunRepertoar(String filnavn) 
    throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filnavn));
        HashMap<String, Subsekvens> map = new HashMap<String, Subsekvens>();
        String linje;
        String sekvens;
        try
        {
            while(in.hasNextLine())
            {
                linje = in.nextLine();
                for(int i=0; i<=linje.length()-sekvensLengde; i++)
                {
                    sekvens = linje.substring(i,i+sekvensLengde);
                    if(!map.containsKey(sekvens))
                    {
                        map.put(sekvens, new Subsekvens(sekvens));
                    }
                }
            }
        }
        finally{in.close();}
        return map;
    }

    /** Slår sammen to HashMap<String,Subsekvens> til en, hvis nykkelen
     * finnes i begge HashMapsen oppdateres instans-variabelen 'antall'
     * for tilhørende Subsekvens-objekt.
     * @param map1
     * @param map2
     * @return den sammenslådde HashMapen
     */
    public static HashMap<String, Subsekvens> slåSammen(HashMap<String,Subsekvens> map1, HashMap<String,Subsekvens> map2)
    {
        HashMap<String, Subsekvens> map = new HashMap<String, Subsekvens>(map1);
        for(String sekvens : map2.keySet())
        {
            if(map.containsKey(sekvens)){map.get(sekvens).endreAntall(map2.get(sekvens).hentAntall());}
            else{map.put(sekvens, map2.get(sekvens));}
        }
        return map;
    }
}