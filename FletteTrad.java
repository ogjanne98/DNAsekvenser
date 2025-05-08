import java.util.*;


/** Tråd som tar ut to og to HashMaps fra en monitor, fletter de sammen
 * og legger den sammenflettede HashMapen tilbake i monitoren. 
 * Terminerer når det bare er en HashMap igjen. */
public class FletteTrad extends Thread
{
    private Monitor2 monitor;

    public FletteTrad(Monitor2 monitor){this.monitor = monitor;}

    @Override public void run()
    {
        while(!isInterrupted())
        {
            try
            {
                ArrayList<HashMap<String,Subsekvens>> map = monitor.hentUtTo();
                monitor.leggTil(Monitor2.slåSammen(map.get(0),map.get(1)));
            }
            catch(InterruptedException exe){this.interrupt();} // bare en HashMap igjen, terminere tråden
        }
    }
}