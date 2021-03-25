package aatral.warzone.statePatternTest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Test;

import aatral.warzone.model.Continent;
import aatral.warzone.statePattern.MasterMapEditor;

public class MasterMapEditorTest {

	@Test
	public void loadMapTest()
	{
		MasterMapEditor mme = new MasterMapEditor();
		//GamePlayer gp1 = new GamePlayer();
		Map<String, Continent> actual = mme.loadMap("canada");
		
	assertNotNull(actual);
		
	}

}
