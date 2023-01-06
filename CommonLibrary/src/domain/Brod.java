
package domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author anape
 */
public class Brod implements Serializable {

    private String name;
    private Koordinate coordinates;
    private int length;
    private boolean vertical;
    private int neotkrivenaPolja;

    public Brod(int length) {
        this.length = length;
    }

    public Brod(String name, int length) {
        this.name = name;
        this.length = length;
        this.neotkrivenaPolja = length;
    }

    public Brod(String name, Koordinate coordinates, int length, boolean vertical, int neotkrivenaPolja) {
        this.name = name;
        this.coordinates = coordinates;
        this.length = length;
        this.vertical = vertical;
        this.neotkrivenaPolja = neotkrivenaPolja;
    }

    public boolean daLiJeOtkriven() {
        return getNeotkrivenaPolja() > 0;
    }

    public void hit() {
        if (daLiJeOtkriven()) {
            this.setNeotkrivenaPolja(this.getNeotkrivenaPolja() - 1);
        }
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Brod other = (Brod) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the coordinates
     */
    public Koordinate getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates the coordinates to set
     */
    public void setCoordinates(Koordinate coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the vertical
     */
    public boolean isVertical() {
        return vertical;
    }

    /**
     * @param vertical the vertical to set
     */
    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    /**
     * @return the neotkrivenaPolja
     */
    public int getNeotkrivenaPolja() {
        return neotkrivenaPolja;
    }

    /**
     * @param fieldsAlive the neotkrivenaPolja to set
     */
    public void setNeotkrivenaPolja(int neotkrivenaPolja) {
        this.neotkrivenaPolja = neotkrivenaPolja;
    }

}
