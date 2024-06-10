package fr.brgm.mapClient.monitoring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@ToString
@Schema(name = "ServiceRequestDTO", description = "ServiceDTO we need to create in order to monitor")
public class ServiceRequestDTO {

    /**
     * ServiceDTO name
     */
    @Schema(description = "Name of the service")
    @NotEmpty
    private String name;

    /**
     * ServiceDTO URL
     */
    @Schema(description = "URL of the service")
    @NotEmpty
    private String url;

    /**
     * Name of the scenario
     */
    @Schema(description = "Name of the scenario")
    @NotEmpty
    private String scenarioName;

    /**
     * Name of the step (contained by the scenario)
     */
    @Schema(description = "Name of the step (contained by the scenario)")
    @NotEmpty
    private String stepName;

    /**
     * Status code required by the the step in order to check the URL
     */
    @Schema(description = "Status code required by the the step in order to check the URL")
    @NotEmpty
    private String stepStatusCode;

    /**
     * Name of the trigger referencing the scenario
     */
    @Schema(description = "Name of the trigger referencing the scenario")
    @NotEmpty
    private String triggerName;
}
