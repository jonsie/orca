package com.netflix.spinnaker.orca.remote.pipeline

import com.netflix.spinnaker.kork.annotations.Beta
import com.netflix.spinnaker.orca.api.pipeline.graph.StageDefinitionBuilder
import com.netflix.spinnaker.orca.api.pipeline.graph.StageDefinitionBuilder.Aliases
import com.netflix.spinnaker.orca.api.pipeline.graph.TaskNode
import com.netflix.spinnaker.orca.api.pipeline.models.StageExecution
import com.netflix.spinnaker.orca.remote.model.RemoteStageExtensionConfig
import com.netflix.spinnaker.orca.remote.service.RemoteStageExtensionService
import com.netflix.spinnaker.orca.remote.tasks.MonitorRemoteStageTask
import com.netflix.spinnaker.orca.remote.tasks.StartRemoteStageTask
import org.springframework.stereotype.Component

@Beta
@Component
@Aliases("remoteStage")
class RemoteStage(
  private val remoteStageExtensionService: RemoteStageExtensionService
) : StageDefinitionBuilder {

  override fun taskGraph(stage: StageExecution, builder: TaskNode.Builder) {
    val remoteExtensionConfig = remoteStageExtensionService
      .getByStageType(stage.type)
      .getTypedConfig<RemoteStageExtensionConfig>()

    // Set default parameters if necessary
    remoteExtensionConfig.parameters.forEach { (k, v) ->
      if (stage.context[k] == null) {
        stage.context[k] = v
      }
    }

    builder
      .withTask("startRemoteStage", StartRemoteStageTask::class.java)
      .withTask("monitorRemoteStage", MonitorRemoteStageTask::class.java)
  }
}
