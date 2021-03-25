package aatral.warzone.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter


/**
 * <h1>Borders</h1> The Class has border values of dependency map
 *
 * @author Vignesh
 * @version 1.0
 * @since 24-02-2021
 */
public class Borders {

	public Borders(InputBorders l_Borders) {
		this.adjacentCountries= l_Borders.getAdjacentCountries();
	}

	Set<String> adjacentCountries = new HashSet<>();

}
