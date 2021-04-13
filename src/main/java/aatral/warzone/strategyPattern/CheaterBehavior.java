package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;

public class CheaterBehavior extends PlayerStrategy{
	public CheaterBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		return null;
	}
}
