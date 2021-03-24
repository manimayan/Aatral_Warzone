package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aatral.warzone.gameplayTest.GameEngineTest;
import aatral.warzone.gameplayTest.GamePlayerTest;
import aatral.warzone.mapeditorTest.ComposeGraphImplTest;
import aatral.warzone.mapeditorTest.EditMapTest;
import aatral.warzone.mapeditorTest.MapEditorTest;
import aatral.warzone.mapeditorTest.ValidateMapImplTest;
import aatral.warzone.utilitiesTest.ContinentMapReaderTest;
import aatral.warzone.utilitiesTest.CountryBorderReaderTest;
import aatral.warzone.utilitiesTest.GraphTest;
import aatral.warzone.utilitiesTest.InputProcessorTest;

@RunWith(Suite.class)
@SuiteClasses({ComposeGraphImplTest.class,
	//EditMapTest.class,
	//MapEditorTest.class,
	ValidateMapImplTest.class,
	ContinentMapReaderTest.class,
	CountryBorderReaderTest.class,
	GraphTest.class,
	GameEngineTest.class,
	InputProcessorTest.class,
	GamePlayerTest.class,
	

	})

public class TestSuite {

}
