package TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aatral.warzone.adapterPatternTest.ConquestMapAdapterTest;
import aatral.warzone.adapterPatternTest.ConquestMapReaderTest;
import aatral.warzone.adapterPatternTest.DominationMapReaderTest;
import aatral.warzone.gameplayTest.AdvanceOrderTest;
import aatral.warzone.gameplayTest.GameEngineTest;
//import aatral.warzone.gameplayTest.GamePlayerTest;
import aatral.warzone.mapeditorTest.ComposeGraphImplTest;
import aatral.warzone.mapeditorTest.ValidateMapImplTest;
import aatral.warzone.statePatternTest.GameplayIssueOrderTest;
import aatral.warzone.statePatternTest.MasterMapEditorTest;
import aatral.warzone.strategyPatternTest.AggresiveBehaviorTest;
import aatral.warzone.strategyPatternTest.HumanBehaviorTest;
import aatral.warzone.strategyPatternTest.SortbyArmiesTest;
import aatral.warzone.utilitiesTest.GraphTest;
import aatral.warzone.utilitiesTest.InputProcessorTest;
import aatral.warzone.utilitiesTest.MapReaderTest;

@RunWith(Suite.class)
@SuiteClasses({ComposeGraphImplTest.class,
	//EditMapTest.class,
	//MapEditorTest.class,
	ValidateMapImplTest.class,
	MapReaderTest.class,
	MapReaderTest.class,
	GraphTest.class,
	GameEngineTest.class,
	InputProcessorTest.class,
	GameplayIssueOrderTest.class,
	MasterMapEditorTest.class,
	AdvanceOrderTest.class,
	DominationMapReaderTest.class,
	ConquestMapReaderTest.class,
	ConquestMapAdapterTest.class,
	SortbyArmiesTest.class,
	AggresiveBehaviorTest.class,
	HumanBehaviorTest.class
	

	})

public class TestSuite {

}
