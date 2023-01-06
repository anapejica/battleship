package util;

import domain.Koordinate;
import domain.Map;
import domain.Brod;
import java.util.LinkedList;

/**
 *
 * @author anape
 */
public class Brodovi {

    public static LinkedList<Brod> getBrodovi() {
        LinkedList<Brod> ships = new LinkedList<>();
        ships.add(new Brod("Borbeni brod - 4", 4));
        ships.add(new Brod("Krstarica 1 - 3", 3));
        ships.add(new Brod("Krstarica 2 - 3", 3));
        ships.add(new Brod("Razarac 1 - 2", 2));
        ships.add(new Brod("Razarac 2 - 2", 2));
        ships.add(new Brod("Razarac 3 - 2", 2));
        ships.add(new Brod("Podmornica 1 - 1", 1));
        ships.add(new Brod("Podmornica 2 - 1", 1));
        ships.add(new Brod("Podmornica 3 - 1", 1));
        ships.add(new Brod("Podmornica 4 - 1", 1));

        return ships;
    }

    public static boolean mozeLiBitiPostavljenBrod(Brod brod, LinkedList<Brod> brodovi, Map yourMap) {
        if (!brodovi.isEmpty()) {
            int y = brod.getCoordinates().getKolona();
            int x = brod.getCoordinates().getRed();

            if (!brod.isVertical()) {
                for (int i = y; i < y + brod.getLength(); i++) {
                    if (!daLiJeValidnoMesto(new Koordinate(x, i))) {
                        return false;
                    }
                    if (yourMap.getBrodNaPoziciji(x, i) != null) {
                        return false;
                    }
                    for (Brod komsija : getKomsije(x, i, yourMap)) {
                        if (komsija != null && komsija != brod) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = x; i < x + brod.getLength(); i++) {
                    if (!daLiJeValidnoMesto(new Koordinate(i, y))) {
                        return false;
                    }
                    if (yourMap.getBrodNaPoziciji(i, y) != null) {
                        return false;
                    }
                    for (Brod neighbour : getKomsije(i, y, yourMap)) {
                        if (neighbour != null && neighbour != brod) {
                            return false;
                        }
                    }
                }
            }
            return true;

        }
        return false;
    }

    private static boolean daLiJeValidnoMesto(Koordinate coordinates) {
        return coordinates.getRed() >= 0 && coordinates.getRed() < 10
                && coordinates.getKolona() >= 0 && coordinates.getKolona() < 10;
    }

    private static LinkedList<Brod> getKomsije(int row, int col, Map yourMap) {
        LinkedList<Brod> komsije = new LinkedList<>();

        if (daLiJeValidnoMesto(new Koordinate(row - 1, col - 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row - 1, col - 1);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row - 1, col + 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row - 1, col + 1);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row - 1, col))) {
            Brod ship = yourMap.getBrodNaPoziciji(row - 1, col);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row + 1, col))) {
            Brod ship = yourMap.getBrodNaPoziciji(row + 1, col);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row, col - 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row, col - 1);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row, col + 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row, col + 1);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row + 1, col + 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row + 1, col + 1);
            komsije.add(ship);
        }
        if (daLiJeValidnoMesto(new Koordinate(row + 1, col - 1))) {
            Brod ship = yourMap.getBrodNaPoziciji(row + 1, col - 1);
            komsije.add(ship);
        }
        return komsije;
    }
}
