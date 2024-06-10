package fr.brgm.mapClient.monitoring.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Schema(name = "TemplateRequestDTO", description = "template containing services we need to create in zabbix in order to monitor services SLA")
public class TemplateRequestDTO {

    /**
     * Host Id. Template belongs to this host
     */
    @Schema(description = "Host Id. Template belongs to this host")
    @NotNull
    private Long hostId;

    /**
     * Group Id. Template belongs to this group
     */
    @Schema(description = "Group Id. Template belongs to this group")
    @NotNull
    private Long groupId;

    /**
     * Template name in zabbix
     */
    @Schema(name = "Template name in zabbix")
    @NotEmpty
    private String templateName;

    /**
     * Application name in zabbix
     */
    @Schema(name = "Application name in zabbix")
    @NotEmpty
    private String applicationName;

    /**
     * Services used to get SLA
     */
    @Schema(name = "List of all services we need to monitor")
    @NotNull
    private List<ServiceRequestDTO> services;
}
