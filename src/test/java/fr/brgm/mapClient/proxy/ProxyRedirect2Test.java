package fr.brgm.mapClient.proxy;

import java.net.URI;
import org.approvaltests.Approvals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProxyRedirect2Test {

    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    void expectedResultFromThematicKeywordsUrl() throws Exception {
        URI uri = new URI("/proxyxml?url=http%3A//onegeology-europe.brgm.fr/eXist/rest//db/1GG_client_registry/thematic_keywords_sauv.xml&_=1709904897622");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Approvals.verify(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verifyXml(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verify(responseEntity.getHeaders().getContentType(),Approvals.NAMES.withParameters("header-ct"));
    }
    
    @Test
    void expectedResultFromGetFeatureInfoUrl() throws Exception {
        URI uri = new URI("/proxyxml?url=http%3A//ogc.bgs.ac.uk%3A80/digmap625k_gsml_insp_gs/ows%3FSERVICE%3DWMS%26VERSION%3D1.3.0%26REQUEST%3DGetFeatureInfo%26FORMAT%3Dimage%252Fpng%26TRANSPARENT%3Dtrue%26QUERY_LAYERS%3Dgsmlp%253AGBR_BGS_625k_BLT_INSP%26LAYERS%3Dgsmlp%253AGBR_BGS_625k_BLT_INSP%26INFO_FORMAT%3Dtext%252Fhtml%26I%3D50%26J%3D50%26CRS%3DEPSG%253A4326%26STYLES%3D%26WIDTH%3D101%26HEIGHT%3D101%26BBOX%3D52.358324171923684%252C-1.557446544145394%252C52.412008973273245%252C-1.5037617427958387&_=1709904897658");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Approvals.verify(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verifyHtml(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verify(responseEntity.getHeaders().getContentType(),Approvals.NAMES.withParameters("header-ct"));
    }
    
    @Test
    void expectedResultFromGetCapabilitiesUrl() throws Exception {
        URI uri = new URI("/proxyxml?url=https%3A%2F%2Fmap.bgs.ac.uk%2Farcgis%2Fservices%2FAGA%2FBGS_Groundwater%2FMapServer%2FWmsServer%3Fservice%3DWMS%26request%3DGetCapabilities%26version%3D1.3.0%26");
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(uri , String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Approvals.verify(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verifyXml(responseEntity.getBody(),Approvals.NAMES.withParameters("body"));
        Approvals.verify(responseEntity.getHeaders().getContentType(),Approvals.NAMES.withParameters("header-ct"));
    }
    
}
