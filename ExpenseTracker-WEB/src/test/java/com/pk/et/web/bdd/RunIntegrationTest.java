package com.pk.et.web.bdd;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/com/pk/et/web/bdd/scenarios")
public class RunIntegrationTest {
}
