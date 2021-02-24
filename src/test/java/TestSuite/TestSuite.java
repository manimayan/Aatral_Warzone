package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aatral.warzone.implementationTest.ComposeGraphTest;
import aatral.warzone.implementationTest.EditMapTest;
import aatral.warzone.implementationTest.MapEditorTest;
import aatral.warzone.implementationTest.ValidateMapTest;
import aatral.warzone.utilitiesTest.ContinentMapReaderTest;
import aatral.warzone.utilitiesTest.CountryBorderReaderTest;
import aatral.warzone.utilitiesTest.GraphTest;
import aatral.warzone.utilitiesTest.InputProcessorTest;

@RunWith(Suite.class)
@SuiteClasses({ComposeGraphTest.class,
	//EditMapTest.class,
	//MapEditorTest.class,
	ValidateMapTest.class,
	ContinentMapReaderTest.class,
	CountryBorderReaderTest.class,
	GraphTest.class,
	//InputProcessorTest.class,
	
	

	})

public class TestSuite {

}
