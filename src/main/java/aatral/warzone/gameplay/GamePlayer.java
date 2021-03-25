package aatral.warzone.gameplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;

import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Record
/**
 * 
 * @author William Moses
 * @version 1.0
 * @since 24-02-2021
 */
public class GamePlayer{

	public String playerName;

	public List<Countries> listOfCountries;

	public int reinforcementArmies;

	public List<Order> orderObjects;
	
	public HashMap<String, Integer> specialCards;

	public boolean advanceInput;
/**
 * GamePlayer method is used to assign the player values for the game
 * @param playerName player name
 * @param listOfCountries list of countries
 * @param armies number of armies
 */
	public GamePlayer(String playerName, List<Countries> listOfCountries, int armies) {
		this.orderObjects = new ArrayList<>();
		this.specialCards = new HashMap<>();
		this.specialCards.put("bomb", 0);
		this.specialCards.put("blockade", 0);
		this.specialCards.put("airlift", 0);
		this.specialCards.put("negotiate", 0);
		this.playerName = playerName;
		this.listOfCountries=listOfCountries;
		this.reinforcementArmies=armies;
		Collections.sort(this.listOfCountries, new Comparator<Countries>() {
			@Override
			public int compare(Countries o1, Countries o2) {
				return Integer.parseInt(o1.getCountryId()) - Integer.parseInt(o2.getCountryId());
			}
		});
	}
	

	/**
	 * ownedCountries method is used to get the list of countries owned by the
	 * player
	 * 
	 * @return getListOfCountries
	 */
	public List<Countries> ownedCountries() {
		return getListOfCountries();
	}

	/**
	 * setAdvanceInput method is used to set the advance input value
	 * @param value values
	 */
	public void setAdvanceInput(boolean value) {
		this.advanceInput = value;
	}
/**
 * getAdvanceInput method is used to get the advance input value
 * @return advance input
 */
	public boolean getAdvanceInput() {
		return advanceInput;
	}

	/**
	 * NextOrder method is used to display list of orders will be executed
	 * 
	 * @return orderObj
	 */
	public Order NextOrder() {
		Order orderObj = this.orderObjects.get(0);
		this.orderObjects.remove(0);
		return orderObj;
	}
/**
 * pushBackOrder method is used to push the order of the game
 * @param p_orderObject order object
 */
	public void pushBackOrder(Order p_orderObject) {
		this.orderObjects.add(0, p_orderObject);
	}

//
//
//	/**
//	 * executeOrders method is used to decide the country attack order
//	 * 
//	 * @param gameplayObject object of gameplayer class.
//	 */
//	public void executeOrders(GamePlayer gameplayObject) {
//		NextOrder();
//		System.out.println("Player " + gameplayObject.getPlayerName() + "'s Orders executed !!!");
//	}

	

	

	

}