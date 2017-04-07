package com.netflix.spinnaker.orca.load

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder

object OrcaActions {

	def postTask: HttpRequestBuilder =
		http("Submit a task to the Orca thingy")
			.post("applications/spintest/tasks")
			.body(StringBody("""{"task":${task}}"""))
			.check(status is 200)
}
