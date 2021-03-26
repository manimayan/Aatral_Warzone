package aatral.warzone.statePattern;

import java.util.Map;

import aatral.warzone.gameplay.GameEngine;
import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Countries;


/**
 * <h1>GamePlay</h1> This abstract class implements the state pattern for game play
 *
 * @author Vignesh
 * @version 1.0
 * @since 24-02-2021
 */
public abstract class GamePlay extends Phase {
	
	/**
	 * showMap method is used to display the entire map
	 */
	@Override
	public void gamePlayShowMap() {
		if (gameEngine.l_playerList.isEmpty()) {
			System.out.println("\nNo player has been created to show the map\n");
		} else {
			for (Map.Entry l_gamePlayer : gameEngine.l_playerObjectList.entrySet()) {
				System.out.println("-------------------------------------------------------------------------------");
				System.out.printf("%-14s%-40s%-12s%-32s\n", "Country ID", "Country Name", "Armies", "Owner");
				System.out.println("-------------------------------------------------------------------------------");
				for (Countries l_co : ((GamePlayer) l_gamePlayer.getValue()).getListOfCountries()) {
					System.out.printf("%-14s%-40s%-12s%-32s\n", "\n" + l_co.getCountryId(), l_co.getCountryName(),
							l_co.getArmies(), l_gamePlayer.getKey());
				}
				System.out.println();
			}
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("%-14s%-40s%-12s%-20s%-100s\n", "Country ID", "Country Name", "Armies", "Owner",
					"Bordering Countries");
			System.out.println(
					"---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			if(GameEngine.l_neutralCountries.size()!=0) {
				for (Countries l_co : GameEngine.l_neutralCountries) {
					System.out.printf("%-14s%-40s%-12s%-20s%-100s\n", "\n" + l_co.getCountryId(), l_co.getCountryName(),
							l_co.getArmies(), "Neutral", gameEngine.countriesUnderPlayerAsString(l_co));
				}
				System.out.println();
			}
		}
	}
}
