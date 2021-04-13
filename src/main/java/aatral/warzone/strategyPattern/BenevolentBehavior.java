package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;

public class BenevolentBehavior extends PlayerStrategy{
	public BenevolentBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		return null;
	}
}
