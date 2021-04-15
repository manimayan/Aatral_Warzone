package aatral.warzone.strategyPatternTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aatral.warzone.strategyPattern.HumanBehavior;
import aatral.warzone.strategyPattern.PlayerStrategy;

public class HumanBehaviorTest {
	@Test
	public void classCheck()
	{
		HumanBehavior hb = new HumanBehavior(null);
		//boolean actual = PlayerStrategy.class.isAssignableFrom(HumanBehavior);
		boolean actual = hb instanceof PlayerStrategy;
		assertTrue(actual);
	}

}
