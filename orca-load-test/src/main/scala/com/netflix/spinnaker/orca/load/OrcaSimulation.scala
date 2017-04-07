package com.netflix.spinnaker.orca.load

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.core.feeder.{FeederBuilder, RecordSeqFeederBuilder}
import io.gatling.core.structure.PopulationBuilder


import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer

class OrcaSimulation extends Simulation {

	val config = new OrcaSimulationConfig(OrcaSimulationConfig.loadConfig())

	val initialized = new {
		var taskTypes: IndexedSeq[String] =  IndexedSeq.empty[String]
	}

	lazy val CircularTaskFeeder: FeederBuilder[String] = RecordSeqFeederBuilder(
		initialized.taskTypes.map { taskType =>
			Map(
				"type" -> taskType
			)
		}

	).circular

	def dataSetup(): Unit = {
		// data setup required for building up tasks
	}

	setUp {
		dataSetup()
		createScenarioList()
	}

	def createScenarioList() : List[PopulationBuilder] = {
		val scenarios: ListBuffer[PopulationBuilder] = new ListBuffer()

		if(config.submitTask.rampUsersTo > 0) {
			scenarios.append(
				OrcaScenarios.submitTask(CircularTaskFeeder).inject(
					rampUsersPerSec(config.submitTask.rampUsersPerSec) to config.submitTask.rampUsersTo during config.rampUpPeriod.seconds,
					constantUsersPerSec(config.submitTask.rampUsersTo) during config.duration
				).protocols(http.baseURL(config.serviceUrl))
			)
		}

		scenarios.toList
	}
}
