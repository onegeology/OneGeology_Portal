package fr.brgm.mapClient.vocabulary;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/vocabulary")
@Tag(name = "/vocabulary", description = "API REST for getting vocabularies")
public class VocabularyController {

    VocabularyService service;

    /**
     * Return the corresponding vocabulary
     * @param name
     * @return The vocabulary content
     */
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Return the corresponding vocabulary")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "The vocabulary content")
    })
    public String getVocabulary(@RequestParam String name) {
       return service.getVocabularyContent(name);
    }
}
