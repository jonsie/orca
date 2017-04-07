package com.netflix.spinnaker.orca.load

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object OrcaSimulationEngine extends App {
	val props = new GatlingPropertiesBuilder
	props.simulationClass(classOf[OrcaSimulation].getName)
	props.resultsDirectory("build/reports/gatling")
	props.binariesDirectory("build/classes/main")

	Gatling.fromMap(props.build)
	sys.exit()
}
