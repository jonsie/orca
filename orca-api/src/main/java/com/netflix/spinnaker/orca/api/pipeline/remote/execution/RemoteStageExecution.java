package com.netflix.spinnaker.orca.api.pipeline.remote.execution;

import com.netflix.spinnaker.kork.annotations.Alpha;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * This object is sent to the remote stage target when initializing the stage.
 */
@Alpha
@Data
@Builder
public class RemoteStageExecution {

  /** The type identifier of the stage. */
  @Nonnull
  private String type;

  /** The stage execution ID as generated by Orca. */
  @Nonnull
  private String id;

  /** The pipeline execution ID which the stage belongs to. */
  @Nonnull
  private String pipelineExecutionId;

  /** The stage context provided at stage initialization. */
  @Nonnull
  private Map<String, Object> context;

  /** All upstream stage outputs ordered by stage refId. */
  @Nonnull
  private Map<String, Map<String, Object>> upstreamStageOutputs;

  /** The {@link RemoteStageCallback} which provides HTTP and Pubsub callback options. */
  @Nonnull
  private RemoteStageCallback callback;
}
