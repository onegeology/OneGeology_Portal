package fr.brgm.mapClient.proxy;

import java.net.URI;
import org.approvaltests.Approvals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProxyCatalogTest {

    @Autowired
    private TestRestTemplate restTemplate;
    
    String timestampAttribute = "timestamp=\"[^\"]*\"";

    @Test
    public void postExampleCatalogRequest() throws Exception {
        URI uri = new URI("/proxycatalog?url=http://onegeology-geonetwork.brgm.fr/geonetwork3/srv/fre/csw");
        // Use below to test sending request to production server
        //URI uri = new URI("https://portal.onegeology.org/OnegeologyGlobal/proxycatalog?url=http://onegeology-geonetwork.brgm.fr/geonetwork3/srv/fre/csw");
        // Use below to test sending request direct to BRGM catalog
        //URI uri = new URI("http://onegeology-geonetwork.brgm.fr/geonetwork3/srv/fre/csw");
        String cswQuery = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <csw:GetRecords xmlns:csw=\"http://www.opengis.net/cat/csw/2.0.2\" service=\"CSW\" version=\"2.0.2\" resultType=\"results\" maxRecords=\"1\" startPosition=\"1\"> <csw:Query typeNames=\"csw:Record\"> <csw:ElementSetName>full</csw:ElementSetName> <csw:Constraint version=\"1.1.0\"> <Filter xmlns=\"http://www.opengis.net/ogc\" xmlns:gml=\"http://www.opengis.net/gml\"> <PropertyIsEqualTo> <PropertyName>dc:identifier</PropertyName> <Literal>b94347eedee9f67d8b0292fcef6a84b8b7911a68</Literal> </PropertyIsEqualTo> </Filter> </csw:Constraint> </csw:Query> </csw:GetRecords>";
        RequestEntity<String> request = RequestEntity.post(uri).contentType(MediaType.TEXT_XML).body(cswQuery);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(request, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String tsRemovedResponse = responseEntity.getBody().replaceFirst(timestampAttribute, "");
        Approvals.verify(tsRemovedResponse,Approvals.NAMES.withParameters("body"));
        Approvals.verifyXml(tsRemovedResponse,Approvals.NAMES.withParameters("body"));
        Approvals.verify(responseEntity.getHeaders().getContentType(),Approvals.NAMES.withParameters("header-ct"));
    }

}
