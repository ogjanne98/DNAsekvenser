import java.io.*;

/** Tråd som leser in en fil og legger til resulterende HashMap i 
 * en monitor for klassen SubsekvensRegister.
 */
public class LeseTrad extends Thread
{
    private Monitor1 monitor;
    private String filnavn;

    public LeseTrad(Monitor1 monitor, String filnavn)
    {
        this.monitor = monitor;
        this.filnavn = filnavn;
    }

    @Override public void run()
    {
        try{monitor.leggTil(Monitor1.lesImmunRepertoar(filnavn));}
        catch(FileNotFoundException exe){System.out.println(exe);}
    }
}