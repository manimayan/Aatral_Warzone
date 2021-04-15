package aatral.warzone.strategyPatternTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import aatral.warzone.model.Countries;

/**
 * <h1>SortbyArmiesTest class holds test fucntions of armies</h1>
 * @author Vignehs senthilkumar
 * @since 13.04.2021
 *
 */
public class SortbyArmiesTest {
	class SortbyArmies implements Comparator<Countries> {
		public int compare(Countries a, Countries b) {
			return b.getArmies() - a.getArmies();
		}
	}

@Test
/**
 * compareTest method is used to compare and test the country values
 */
public void compareTest()
{
	SortbyArmies sba = new SortbyArmies();
	Countries p1 = new Countries();
	//p1.getArmies();
	Countries p2 = new Countries();
	//p2.getArmies();
	int actual = sba.compare(p1,p2);
	System.out.println(actual);
	assertTrue( Integer.class.isInstance(actual));
}
@Test
/**
 * compareTestPositive method is used to compare and test the positive country values
 */
public void compareTestPositive()
{
	SortbyArmies sba = new SortbyArmies();
	Countries p1 = new Countries();
	p1.setArmies(4);
	Countries p2 = new Countries();
	p2.setArmies(6);
	int actual = sba.compare(p1,p2);
	System.out.println(actual);
	assertTrue( actual>0);
}
@Test
/**
 * compareTestNegative method is used to compare and test the negative country values
 */
public void compareTestNegative()
{
	SortbyArmies sba = new SortbyArmies();
	Countries p1 = new Countries();
	p1.setArmies(4);
	Countries p2 = new Countries();
	p2.setArmies(3);
	int actual = sba.compare(p1,p2);
	System.out.println(actual);
	assertFalse( actual>0);
}
}
