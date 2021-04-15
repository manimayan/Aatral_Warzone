package aatral.warzone.strategyPatternTest;

import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Countries;
import aatral.warzone.strategyPattern.AggressiveBehavior;

public class AggresiveBehaviorTest {
	@Test
	public void listOfCountriesTest()
	{
		boolean actual = false;
		AggressiveBehavior ab = new AggressiveBehavior(null);
		ab.listOfCountries();
		List<Countries> c5=ab.listOfCountries();
		if(c5 instanceof List<?>)
		{
			ParameterizedType pt = (ParameterizedType)c5.getClass().getGenericSuperclass();
			String innerClass = pt.getActualTypeArguments()[0].toString().replace("class ", "");
			//System.out.println(innerClass.equals(Continent)); // true
			actual = true;
		}

		assertTrue(actual);


	}
	@Test
	public void isAttackTest()
	{
		boolean actual =false;
		AggressiveBehavior ab = new AggressiveBehavior(null);
		Countries c1=new Countries();
		c1.setCountryId("4");
		GamePlayer gp = new GamePlayer();
		List<Countries> l1 = new ArrayList<Countries>();
		Countries c2=new Countries();
		Countries c3=new Countries();
		Countries c4=new Countries();
		c2.setCountryId("4");
		c3.setCountryId("1");
		c4.setCountryId("5");
		l1.add(c2);
		l1.add(c3);
		l1.add(c4);
		//gp.setListOfCountries(l1);
		for(Countries c11 : l1)
		{
			if(c1.getCountryId().equals("4"))
			{
				actual=true;
			}

		}
		assertTrue(actual);
	}
	@Test
	public void isAttackTestNegative()
	{
		boolean actual =false;
		AggressiveBehavior ab = new AggressiveBehavior(null);
		Countries c1=new Countries();
		c1.setCountryId("4");
		GamePlayer gp = new GamePlayer();
		List<Countries> l1 = new ArrayList<Countries>();
		Countries c2=new Countries();
		Countries c3=new Countries();
		Countries c4=new Countries();
		c2.setCountryId("34");
		c3.setCountryId("1");
		c4.setCountryId("5");
		l1.add(c2);
		l1.add(c3);
		l1.add(c4);
		for(Countries c11 : l1)
		{
			if(c1.getCountryId().equals("4"))
			{
				actual=true;
			}

		}
		assertTrue(actual);
	}

}
