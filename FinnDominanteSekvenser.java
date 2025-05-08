import java.util.*;
import java.io.*;

public class FinnDominanteSekvenser
{
    private static final int antallFletteTrader = 8;
    private static final int grense = 7; // forskjell antall subsekvenser infekterte vs friske

    /** Tar stien til mappen med testfilene som command-line argument */
    public static void main(String[] args)
    {
        try
        {
            String meta = "metadata.csv";
            String testFil = args[0] + "/" + meta;
            ArrayList<ArrayList<String>> filer = FinnDominanteSekvenser.lesMetadata(testFil);
            Monitor2 infekterte = new Monitor2(new SubsekvensRegister());
            Monitor2 friske = new Monitor2(new SubsekvensRegister());
            ArrayList<LeseTrad> lesetrader = new ArrayList<LeseTrad>();
            for(int i=0; i<2; i++)
            {
                for(String fil : filer.get(i))
                {
                    if(i==0){lesetrader.add(new LeseTrad(infekterte, args[0]+"/"+fil));}
                    else{lesetrader.add(new LeseTrad(friske, args[0]+"/"+fil));}
                    lesetrader.get(lesetrader.size()-1).start();
                }
            }
            for(LeseTrad trad : lesetrader){trad.join();}
            FletteTrad[] infekterteFletteTrader = new FletteTrad[antallFletteTrader];
            FletteTrad[] friskeFletteTrader = new FletteTrad[antallFletteTrader];
            for(int i=0; i<antallFletteTrader; i++)
            {
                infekterteFletteTrader[i] = new FletteTrad(infekterte);
                friskeFletteTrader[i] = new FletteTrad(friske);
                infekterteFletteTrader[i].start();
                friskeFletteTrader[i].start();
            }
            for(int i=0; i<antallFletteTrader; i++)
            {
                infekterteFletteTrader[i].join();
                friskeFletteTrader[i].join();
            }
            System.out.println(FinnDominanteSekvenser.finnDominante(infekterte.hente(0),friske.hente(0),grense));
        }
        catch(FileNotFoundException exe){System.out.println(exe);}
        catch(InterruptedException exe){System.out.println(exe);}
    }

    /** Leser in en fil med filanvn 
     * @param fil filen som leses in
     * @return ArrayList med to ArrayList av filnavnene, en for infekterte og en for friske
    */
    public static ArrayList<ArrayList<String>> lesMetadata(String filnavn)
    throws FileNotFoundException
    {
        Scanner in = new Scanner(new File(filnavn));
        ArrayList<ArrayList<String>> filer = new ArrayList<ArrayList<String>>();
        filer.add(new ArrayList<String>()); filer.add(new ArrayList<String>());
        String[] linje;
        while(in.hasNextLine())
        {
            linje = in.nextLine().split(",");
            if(linje[1].equals("True")){filer.get(0).add(linje[0]);}
            else{filer.get(1).add(linje[0]);}
        }
        in.close();
        return filer;
    }

    /** Finner de DNA-strenger som forekommer fler ganger hos de infekterte
     * en de friske, hvis forskjellen >= 'grense'.
     * @param map1 HashMap for de infekterte
     * @param map2 HashMap for de friske
     * @param grense forskjell antall DNA-strenger
     * @return liste med DNA-strengene
     */
    public static ArrayList<String> finnDominante(HashMap<String,Subsekvens> map1, HashMap<String,Subsekvens> map2, int grense)
    {
        ArrayList<String> sekvenser = new ArrayList<String>();
        for(String key : map1.keySet())
        {
            if(map2.containsKey(key))
            {
                if(map1.get(key).hentAntall()-map2.get(key).hentAntall() >= grense)
                {sekvenser.add(map1.get(key).toString());}
            }
            else
            {
                if(map1.get(key).hentAntall()>=grense){sekvenser.add(map1.get(key).toString());}
            }
        }
        return sekvenser;
    }
}