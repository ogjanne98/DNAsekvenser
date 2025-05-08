import java.util.*;

/** Utvidelse av Monitor1 slik at bare en FletteTrad i taget 
 * kan hente ut HashMaps */
public class Monitor2 extends Monitor1
{
    private int hentetUt;
    private int lagtTilbake;

    public Monitor2(SubsekvensRegister register)
    {
        super(register);
        hentetUt = 0;
        lagtTilbake = 0;
    }

    /** Henter ut de to siste HashMapsen i HashMap-beholderen. Hvis det
     * er mindre en 2 HashMaps i beholderen, checker metoden hvis det er 
     * andre tråder på vei at legge tilbake HashMaps, og venter isåfall
     * til de andre trådene er ferdige.
     * @return ArrayList med 2 HashMaps
     */
    public synchronized ArrayList<HashMap<String,Subsekvens>> hentUtTo()
    throws InterruptedException
    {
        while(this.antall() < 2 && hentetUt > lagtTilbake){this.wait();}
        if(this.antall() >= 2)
        {
            ArrayList<HashMap<String,Subsekvens>> toMap = new ArrayList<HashMap<String,Subsekvens>>();
            toMap.add(this.hente(0)); 
            toMap.add(this.hente(0));
            hentetUt++;
            return toMap;
        }
        else{throw new InterruptedException();} // bare en HashMap igjen
    }

    /** Hvis en FletteTrad legger tilbake en sammenflettet HashMap,
     * oppdater lagtTilbake variabelen  og si ifra til eventuelt ventende 
     * tråder. 
     * @param map HashMapen som skal legges tilbake
     */
    @Override public synchronized void leggTil(HashMap<String,Subsekvens> map)
    {
        super.leggTil(map);
        if(Thread.currentThread() instanceof FletteTrad)
        {
            lagtTilbake++;
            this.notifyAll();
        }
    }
}