/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author anape
 */
public class Map implements Serializable {

    private Brod[][] grid;

    public Map() {
        grid = new Brod[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = null;
            }
        }
    }

    public Map(Map otherMap) {
        grid = new Brod[10][10];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = otherMap.getBrodNaPoziciji(i, j);
            }
        }
    }

    public Brod[][] getGrid() {
        return grid;
    }

    public void setGrid(Brod[][] newGrid) {
        this.grid = newGrid;
    }

    public Brod getBrodNaPoziciji(int row, int col) {
        return grid[row][col];
    }

    public void setBrodNaPoziciji(int row, int col, Brod ship) {
        grid[row][col] = ship;
    }


    public int brojNeotkrivenihPolja() {
        ArrayList<Brod> neotkriveniBrodovi = new ArrayList<>();
        int neotkrivenaPolja = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Brod ship = grid[i][j];
                if (ship != null && ship.daLiJeOtkriven() && !neotkriveniBrodovi.contains(ship)) {
                    neotkriveniBrodovi.add(ship);
                    neotkrivenaPolja += ship.getNeotkrivenaPolja();
                }
            }
        }

        return neotkrivenaPolja;
    }

    public Boolean updateMap(Koordinate koordinate) {
        int red = koordinate.getRed();
        int kolona = koordinate.getKolona();

        Brod brod = getBrodNaPoziciji(red, kolona);
        if (brod != null) {
            brod.hit();
            return true;
        }

        return false;
    }

    public int brojNeotkrivenihBrodova() {
        ArrayList<Brod> neotkriveniBrodovi = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Brod brod = grid[i][j];
                if (brod != null && brod.daLiJeOtkriven() && !neotkriveniBrodovi.contains(brod)) {
                    neotkriveniBrodovi.add(brod);
                }
            }
        }
        return neotkriveniBrodovi.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Map map = (Map) o;

        return Arrays.deepEquals(getGrid(), map.getGrid());
    }

    public void printMap() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(grid[i][j] != null ? 1 + "\t" : 0 + "\t");
            }
            System.out.println();
        }
    }
}
