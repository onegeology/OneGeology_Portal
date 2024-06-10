package fr.brgm.mapClient.wms.validation;

import fr.brgm.mapClient.wms.validation.dto.WmsStatusDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.NotEmpty;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Rest controller for the validation of external WMS
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/wms/validation")
@Tag(name = "/api/wms/validation", description = "Rest controller for the validation of external WMS")
public class WmsValidationController {

    /**
     * Validation service for external WMS
     */
    private final WmsValidationService wmsValidationService;

    /**
     * Validate an external WMS from its URL
     *
     * @param url URL of the WMS
     * @return The status of the validation
     */
    @PostMapping("/")
    @Operation(summary = "Validate an external WMS from its URL", responses = {
            @ApiResponse(responseCode = "200", description = "The status of the validation")
    })
    public WmsStatusDTO monitoring(@Parameter(name = "URL of the WMS", required = true) @RequestBody @NotEmpty String url) {
        try {
            url = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
        } catch(UnsupportedEncodingException e) {

        }
        return this.wmsValidationService.validateWms(url);
    }
}
