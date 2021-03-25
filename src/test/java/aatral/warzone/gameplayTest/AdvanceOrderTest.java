package aatral.warzone.gameplayTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.awt.List;

import org.junit.Test;

import aatral.warzone.gameplay.AdvanceOrder;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Countries;
import aatral.warzone.statePattern.GamePlay;

public class AdvanceOrderTest {
	/*@Test
	public void isAttackTest()
	{
		GamePlayer gp= new GamePlayer();
		AdvanceOrder ao= new AdvanceOrder();
	java.util.List<Countries> co=	gp.getListOfCountries()
		boolean actual = ao.isAttack(gp,"Nova_Scotia");
		assertNotEquals(true,actual);
		
	}*/
@Test
public void attackerCalcTestFloor()
{
	AdvanceOrder ao= new AdvanceOrder();
	 int expected = 11;
	//System.out.println(ao.attackerCalc("21"));
	 assertEquals(expected,ao.attackerCalc("19"));
}
@Test
public void attackerCalcTestCeil()
{
	AdvanceOrder ao= new AdvanceOrder();
	 int expected = 13;
	System.out.println(ao.attackerCalc("21"));
	 assertEquals(expected,ao.attackerCalc("21"));
}
@Test
public void defenderCalcTest()
{
	AdvanceOrder ao= new AdvanceOrder();
	 int expected = 13;
	//System.out.println(ao.attackerCalc("19"));
	 assertEquals(expected,ao.defenderCalc("19"));
	 
}
@Test

public void defenderCalcTestCeil()
{
	AdvanceOrder ao= new AdvanceOrder();
	 int expected = 15;
	System.out.println(ao.attackerCalc("21"));
	 assertEquals(expected,ao.defenderCalc("21"));
}

}
