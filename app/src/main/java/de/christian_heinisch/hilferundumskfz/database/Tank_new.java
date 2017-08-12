package de.christian_heinisch.hilferundumskfz.database;

/**
 * Created by chris on 29.06.2017.
 */

public class Tank_new {

    public static final int OVERVIEW_TYPE = 0;
    public static final int LIST_TYPE = 1;

    private double euro;
    private double liter;
    private double kilometer;
    private long id;
    private int monat;
    private int jahr;
    private int tag;
    int mType;


    public Tank_new(long id, double Euro, double liter, double kilometer, int jahr, int monat, int tag, int type){

        this.id = id;
        this.euro = Euro;
        this.kilometer = kilometer;
        this.liter = liter;
        this.monat = monat;
        this.tag = tag;
        this.jahr = jahr;
        this.mType = type;

    }

    public long getId(){
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEuro() {
        return euro;
    }

    public void setEuro(double euro) {
        this.euro = euro;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }

    public double getLiter() {
        return liter;
    }

    public void setKilometer(double kilometer) {
        this.kilometer = kilometer;
    }

    public double getKilometer() {
        return kilometer;
    }

    public int getJahr() {
        return jahr;
    }

    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    public int getMonat(){
        return  monat;
    }

    public void setMonat(int monat) {
        this.monat = monat;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getType() {
        return mType;
    }
    public void setType(int type) {
        this.mType = type;
    }

}
