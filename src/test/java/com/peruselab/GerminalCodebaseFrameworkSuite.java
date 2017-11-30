package com.peruselab;

import com.peruselab.client.GerminalCodebaseFrameworkTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class GerminalCodebaseFrameworkSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for GerminalCodebaseFramework");
		suite.addTestSuite(GerminalCodebaseFrameworkTest.class);
		return suite;
	}
}
