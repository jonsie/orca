package com.netflix.spinnaker.orca.load

import java.io.File
import com.typesafe.config.{Config, ConfigFactory}

object OrcaSimulationConfig {
	def loadConfig(): Config = {
		val configFilePath = sys.props.get("simulation.config")

		if (configFilePath.isDefined) {
			val file = new File(configFilePath.get)
			ConfigFactory.parseFile(file)
		} else {
			ConfigFactory.parseResources("orca-simulation.conf")
		}
	}
}

class OrcaSimulationConfig(config: Config) {

	val serviceUrl = config.getString("service.orca.serviceUrl")

	val rampUpPeriod = config.getInt("service.orca.rampUpPeriod")
	val duration = config.getInt("service.orca.duration")


//	val setup = new {
//		val authUsername = config.getString("service.orca.setup.example")
//	}

	val submitTask = new {
		val rampUsersPerSec = config.getInt("service.orca.submitTask.rampUsersPerSec")
		val rampUsersTo = config.getInt("service.orca.submitTask.rampUsersTo")
	}
}
