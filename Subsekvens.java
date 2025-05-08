

/** Klasse som holder antall personer med en gitt DNA-sekvens i sitt
 * immunrepertoar
 */
public class Subsekvens
{
    final public String sekvens;
    private int antall; 

    public Subsekvens(String sekvens)
    {
        this.antall = 1;
        this.sekvens = sekvens;
    }

    public int hentAntall(){return antall;}

    public void endreAntall(int n){antall = antall + n;}

    @Override public String toString()
    {
        return "(" + sekvens + "," + antall + ")";
    }
}