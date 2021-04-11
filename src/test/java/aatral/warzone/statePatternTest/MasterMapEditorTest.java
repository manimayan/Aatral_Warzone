package aatral.warzone.statePatternTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;

import aatral.warzone.model.Continent;
import aatral.warzone.statePattern.MasterMapEditor;

/**
 * MasterMapEditorTest class is used to test the template methods to edit the selected map
 * @author vignesh senthilkumar
 * @since 21.03.2021
 */

public class MasterMapEditorTest {

/**
 * loadMapTest method is used to test the loaded map 
 */
	@Test
	public void loadMapTest()
	{
		MasterMapEditor mme = new MasterMapEditor();
		//GamePlayer gp1 = new GamePlayer();
		Map<String, Continent> actual = mme.loadMap("domination","canada");
		
	assertNotNull(actual);
		
	}

}
