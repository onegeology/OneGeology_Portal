package fr.brgm.mapClient.methodology;

import fr.brgm.mapClient.methodology.dto.M49DTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Rest controller to get M49 methodology
 */
@RestController
@AllArgsConstructor
@RequestMapping("/methodology/m49")
@Tag(name = "/methodology/m49", description = "Controller used to get M49 methodology")
public class M49Controller {

    /**
     * Service to get M49 codes
     */
    private M49Service m49Service;

    /**
     * Get all M49 codes and names
     *
     * @return all M49 codes and names
     */
    @GetMapping("/")
    @Operation(summary = "Get all M49 codes and names")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Response class containing all M49 codes and names")
    })
    public List<M49DTO> readM49MethodologyAsJson() throws IOException {
        return this.m49Service.readM49MethodologyAsJson();
    }

}
