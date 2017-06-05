package de.christian_heinisch.hilferundumskfz.data;

/**
 * Created by chris on 09.10.2016.
 */

public class ListItemMenu {

    public String name;
    public String beschreibung;

    public ListItemMenu(String name, String beschreibung){
        this.name = name;
        this.beschreibung = beschreibung;

    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }


}
