package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;

public class RandomBehavior extends PlayerStrategy{
	public RandomBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		return null;
	}
}