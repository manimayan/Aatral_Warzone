package aatral.warzone.strategyPattern;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.gameplay.Order;

public class HumanBehavior extends PlayerStrategy{
	public HumanBehavior(GamePlayer p_player) {
		super(p_player);
	}

	public Order createOrder() {
		return null;
	}
}