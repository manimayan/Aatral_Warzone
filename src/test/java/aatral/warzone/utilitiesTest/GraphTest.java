package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import aatral.warzone.model.InputCountry;
import aatral.warzone.utilities.Graph;

public class GraphTest {
	static Graph d_Gr = new Graph();
	static HashMap<String, List<InputCountry>> d_Hm = new HashMap<>();

	@Test
	public void addVertexTest() {
		d_Hm.put("1", new ArrayList());
		assertEquals(d_Hm, d_Gr.addVertex("1"));
		System.out.println(d_Hm);
	}

	@Test
	public void addEdgeTest() {
		InputCountry Test = new InputCountry("1", "Afganistan", "1");
		d_Hm.get("1").add(Test);
		assertEquals(d_Hm, d_Gr.addEdge("1", Test));
		System.out.println(d_Hm);
	}

	@Test
	public void removeVertexTest() {
		d_Hm.remove("1");
		d_Gr.removeVertex("1");
		assertEquals(d_Hm, d_Gr.l_adjVertices);
		System.out.println(d_Hm);
	}

}
