package aatral.warzone.save;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aatral.warzone.gameplay.GamePlayer;
import aatral.warzone.model.Continent;
import aatral.warzone.model.Countries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameObjects implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public List<String> l_playerList;
	public HashMap<String, GamePlayer> l_playerObjectList;
	public Map<String, Continent> l_masterMap;
	public String l_mapName;
	public GamePlayer l_gamePlayerObject;
	public String l_gameIssueOrder;
	public boolean l_gamePlayPopulateFlag;
	public boolean l_isFirst;
	public List<Countries> l_neutralCountries;
	public int maxTurns;	
}
