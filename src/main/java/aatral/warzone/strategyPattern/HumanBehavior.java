package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;
/**
 * <h1>HumanBehavior is used to implement behaviour strategy of Human</h1>
 * @author William moses
 * @since 15.04.2021
 */
public class HumanBehavior extends PlayerStrategy{
	public HumanBehavior(GamePlayer p_player) {
		super(p_player);
	}
	/**
	 * createOrder method is used to create the order of the game
	 */
	public Order createOrder() {
		return null;
	}
}