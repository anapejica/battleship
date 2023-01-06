
package wsclient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for game complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="game">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws/}generalDObject">
 *       &lt;sequence>
 *         &lt;element name="idGame" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idUser" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="idWinner" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="end" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="numberOfFieldsLeft" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="numberOfFieldsHit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="score" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "game", propOrder = {
    "idGame",
    "idUser",
    "idWinner",
    "end",
    "numberOfFieldsLeft",
    "numberOfFieldsHit",
    "score"
})
public class Game
    extends GeneralDObject
{

    protected int idGame;
    protected int idUser;
    protected int idWinner;
    protected boolean end;
    protected int numberOfFieldsLeft;
    protected int numberOfFieldsHit;
    protected int score;

    /**
     * Gets the value of the idGame property.
     * 
     */
    public int getIdGame() {
        return idGame;
    }

    /**
     * Sets the value of the idGame property.
     * 
     */
    public void setIdGame(int value) {
        this.idGame = value;
    }

    /**
     * Gets the value of the idUser property.
     * 
     */
    public int getIdUser() {
        return idUser;
    }

    /**
     * Sets the value of the idUser property.
     * 
     */
    public void setIdUser(int value) {
        this.idUser = value;
    }

    /**
     * Gets the value of the idWinner property.
     * 
     */
    public int getIdWinner() {
        return idWinner;
    }

    /**
     * Sets the value of the idWinner property.
     * 
     */
    public void setIdWinner(int value) {
        this.idWinner = value;
    }

    /**
     * Gets the value of the end property.
     * 
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     * 
     */
    public void setEnd(boolean value) {
        this.end = value;
    }

    /**
     * Gets the value of the numberOfFieldsLeft property.
     * 
     */
    public int getNumberOfFieldsLeft() {
        return numberOfFieldsLeft;
    }

    /**
     * Sets the value of the numberOfFieldsLeft property.
     * 
     */
    public void setNumberOfFieldsLeft(int value) {
        this.numberOfFieldsLeft = value;
    }

    /**
     * Gets the value of the numberOfFieldsHit property.
     * 
     */
    public int getNumberOfFieldsHit() {
        return numberOfFieldsHit;
    }

    /**
     * Sets the value of the numberOfFieldsHit property.
     * 
     */
    public void setNumberOfFieldsHit(int value) {
        this.numberOfFieldsHit = value;
    }

    /**
     * Gets the value of the score property.
     * 
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the value of the score property.
     * 
     */
    public void setScore(int value) {
        this.score = value;
    }

}
