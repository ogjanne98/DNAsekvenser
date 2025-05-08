import java.util.*;
import java.io.*;

/**  Monitor for klassen SubsekvensRegister, slik at bare en tråd i taget
 * kan legge til en ny HashMap til registeret. Metoden leggTil er synkronisert.
*/
public class Monitor1
{
    private SubsekvensRegister register;

    public Monitor1(SubsekvensRegister reg){this.register = reg;}

    public SubsekvensRegister hentRegister(){return this.register;}

    public synchronized void leggTil(HashMap<String,Subsekvens> map)
    {this.register.leggTil(map);}

    public HashMap<String,Subsekvens> hente(int indeks){return this.register.hente(indeks);}

    public int antall(){return this.register.antall();}

    public static HashMap<String,Subsekvens> lesImmunRepertoar(String filnavn) 
    throws FileNotFoundException
    {return SubsekvensRegister.lesImmunRepertoar(filnavn);}

    public static HashMap<String, Subsekvens> slåSammen(HashMap<String,Subsekvens> map1, HashMap<String,Subsekvens> map2)
    {return SubsekvensRegister.slåSammen(map1,map2);}
}