package de.christian_heinisch.whatineed.data;

/**
 * Created by chris on 09.10.2016.
 */

public class ListItemOverview {

    public String name;
    public String beschreibung;
    public String bild;
    public String langtext;

    public ListItemOverview(String name, String beschreibung, String bild, String langtext){
        this.name = name;
        this.beschreibung = beschreibung;
        this.bild = bild;
        this.langtext = langtext;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getBild() {
        return bild;
    }

    public String getLangtext() {
        return langtext;
    }

}
