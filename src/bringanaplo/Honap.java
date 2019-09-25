package bringanaplo;

import java.sql.Date;

/**
 *
 * @author Fere
 */
public class Honap {
    private String [] honapok={"" , "január" , "február" , "március" , "április" , "május" , "június" ,
                               "július" , "augusztus" , "szeptember" , "október" , "november" , "december"};
    private Date datum;
    private int ev;
    private String honap;
    private int tav;
    private String ido;
    private int perc;
    private String note;
    private int osszTav;
    private double AVG;
    
    public Honap(Date datum, int tav, String ido, String note, int ossztav) {
        this.datum=datum;
        this.ev = Integer.parseInt(datum.toString().substring(0,4));
        this.honap = honapok[Integer.parseInt(datum.toString().substring(5,7))];
        this.tav = tav;
        this.ido = ido;
        this.perc = Integer.parseInt(ido.substring(0,2))*60+Integer.parseInt(ido.substring(3));
        this.note = note;
        this.osszTav = ossztav;
        if (perc!=0)
            this.AVG=(double)Math.round(tav*6000/perc)/100;
    }

    public Date getDatum() {
        return datum;
    }

    public String[] getHonapok() {
        return honapok;
    }

    public int getEv() {
        return ev;
    }

    public String getHonap() {
        return honap;
    }

    public int getTav() {
        return tav;
    }

    public String getIdo() {
        return ido;
    }

    public String getNote() {
        return note;
    }

    public int getOsszTav() {
        return osszTav;
    }

    public double getAVG() {
        return AVG;
    }

    public int getPerc() {
        return perc;
    }
    
    
}
