package de.christian_heinisch.whatineed.data;

/**
 * Created by chris on 09.10.2016.
 */

public class ListItemOverview {

    public String name;
    public String beschreibung;
    public String bild_gross;
    public String bild_klein;
    public String langtext;

    public ListItemOverview(String name, String beschreibung, String bild_gross, String bild_klein, String langtext){
        this.name = name;
        this.beschreibung = beschreibung;
        this.bild_gross = bild_gross;
        this.bild_klein = bild_klein;
        this.langtext = langtext;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public String getBild_gross() {
        return bild_gross;
    }

    public String getBild_klein() {
        return bild_klein;
    }

    public String getLangtext() {
        return langtext;
    }

}
