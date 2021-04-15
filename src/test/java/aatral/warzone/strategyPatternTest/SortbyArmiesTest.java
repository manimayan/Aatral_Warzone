package aatral.warzone.strategyPatternTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import aatral.warzone.model.Countries;

public class SortbyArmiesTest {
	class SortbyArmies implements Comparator<Countries> {
		public int compare(Countries a, Countries b) {
			return b.getArmies() - a.getArmies();
		}
	}

@Test
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
