package aatral.warzone.strategyPattern;

import java.io.Serializable;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;


/**
 * <h1>PlayerStrategy class hold the abstract values of game player object and create order method</h1>
 * @author William
 * @since 13.04.2021
 */
public abstract class PlayerStrategy  implements Serializable{
	public GamePlayer gamePlayerObject; 
	public abstract Order createOrder();

	public PlayerStrategy(GamePlayer p_player) {
		this.gamePlayerObject = p_player;
	}
}