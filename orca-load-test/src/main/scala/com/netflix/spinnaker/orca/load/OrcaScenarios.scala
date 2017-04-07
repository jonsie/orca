package com.netflix.spinnaker.orca.load

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder

object OrcaScenarios {

	def submitTask(tasks: FeederBuilder[String]): ScenarioBuilder = {
		scenario("Event without subscription")
			.feed(tasks)
			.exec(OrcaActions.postTask)
	}
}
