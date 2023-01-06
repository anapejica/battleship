/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author anape
 */
public enum StanjePolja implements Serializable {
    /**
     * Prazno polje
     */
    PRAZNO,
    /**
     * Polje na koje je postavljen brod
     */
    BROD,
    /**
     * Prazno polje koje je gadjano
     */
    POGODJEN_PRAZAN,
    /**
     * Polje s brodom koje je pogodjeno
     */
    POGODJEN_BROD
}
