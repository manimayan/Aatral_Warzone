package aatral.warzone.utilitiesTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import aatral.warzone.model.Country;
import aatral.warzone.utilities.Graph;

public class GraphTest {
	static Graph gr=new Graph();
	static HashMap <String, List<Country>> hm  = new HashMap<>(); 
	
@Test
	public void addVertexTest()
	{
		hm.put("1",new ArrayList());
		assertEquals(hm, gr.addVertex("1"));
		System.out.println(hm);
	}

@Test
	public void addEdgeTest()
	{
	Country Test = new Country("1","Afganistan","1",null,0);
	hm.get("1").add(Test);
	assertEquals(hm,gr.addEdge("1",Test));
	System.out.println(hm);
	}

@Test
	public void removeVertexTest() 
	{
		hm.remove("1");
		gr.removeVertex("1");
		assertEquals(hm,gr.adjVertices);
		System.out.println(hm);
	}


}
