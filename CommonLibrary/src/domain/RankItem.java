
package domain;

import java.io.Serializable;

/**
 *
 * @author anape
 */
public class RankItem implements Serializable {

    private User user;
    private Game game;

    public RankItem(User user, Game game) {
        this.user = user;
        this.game = game;
    }

    public RankItem() {
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

}
