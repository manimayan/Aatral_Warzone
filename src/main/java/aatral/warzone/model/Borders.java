package aatral.warzone.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Borders {

	public Borders(InputBorders l_Borders) {
		this.adjacentCountries= l_Borders.getAdjacentCountries();
	}

	Set<String> adjacentCountries = new HashSet<>();

}
