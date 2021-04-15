package aatral.warzone.strategyPatternTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aatral.warzone.strategyPattern.HumanBehavior;
import aatral.warzone.strategyPattern.PlayerStrategy;

/**
 * <h1>HumanBehaviorTest class holds test fucntions of humar behavior startegy</h1>
 * @author Vignehs senthilkumar
 * @since 13.04.2021
 *
 */
public class HumanBehaviorTest {
	@Test
	
	/**
	 * classCheck method is used to test the human behavior function
	 */
	public void classCheck()
	{
		HumanBehavior hb = new HumanBehavior(null);
		//boolean actual = PlayerStrategy.class.isAssignableFrom(HumanBehavior);
		boolean actual = hb instanceof PlayerStrategy;
		assertTrue(actual);
	}

}
