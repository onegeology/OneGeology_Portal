package fr.brgm.mapClient.wfs;

import fr.brgm.mapClient.wfs.exception.WfsException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/wfs")
@Tag(name = "/wfs", description = "API REST pour accéder aux scavengers")
public class WfsController {

    private final WfsService wfsService;

    /**
     * Appel de la génération du WFS avec les paramètres en entrée
     * @param request
     * @param bbox
     * @param srs
     * @param lang
     * @param url
     * @param typename
     * @param version
     * @param gsmlVersion
     * @param filter
     * @return Le WFS sous forme de String
     * @throws WfsException Il est impossible de communiquer avec le générateur de WFS
     */
    @PostMapping(value = "/")
    @Operation(description = "Appel de la génération du WFS avec les paramètres en entrée")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Le WFS sous forme de String"),
            @ApiResponse(responseCode = "500", description = "Il est impossible de communiquer avec le générateur de WFS")
    })

    public String getWFS(@RequestParam String request,
                         @RequestParam String bbox,
                         @RequestParam String srs,
                         @RequestParam String lang,
                         @RequestParam String url,
                         @RequestParam String typename,
                         @RequestParam String version,
                         @RequestParam String gsmlVersion,
                         @RequestParam String filter,
                         HttpServletResponse httpServletResponse) throws WfsException {

        if (request.equalsIgnoreCase("gsmlbbox")) {
            httpServletResponse.setContentType("application/xml");
            String ts = Long.toString(System.currentTimeMillis());
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=1GG-GSML-" + ts + ".xml");
        } else if (request.equalsIgnoreCase("stats")) {
            httpServletResponse.setContentType("application/json");
        }

        return this.wfsService.getWfs(request, bbox, srs, lang, url, typename, version, gsmlVersion, filter);
    }
}
