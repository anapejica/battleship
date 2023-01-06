package domain;

import java.io.Serializable;

/**
 *
 * @author anape
 */
public class Koordinate implements Serializable{

    private int red;
    private int kolona;

    public Koordinate() {
    }

    public Koordinate(int red, int col) {
        this.red = red;
        this.kolona = col;
    }

    public Koordinate(Koordinate coordinates) {
        this.red = coordinates.getRed();
        this.kolona = coordinates.getKolona();
    }

    public int getRed() {
        return red;
    }

    public int getKolona() {
        return kolona;
    }

}
