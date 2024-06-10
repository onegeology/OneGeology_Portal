package fr.brgm.mapClient.methodology;

import fr.brgm.mapClient.methodology.dto.M49DTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link M49Service}
 */
@SpringBootTest
public class M49ServiceIntegrationTest {

    /**
     * Service to test
     */
    @Autowired
    private M49Service m49Service;

    /**
     * Testing readM49MethodologyAsJson
     */
    @Test
    public void whenReadM49MethodologyAsJsonShouldMapCorrectlyEachEntry() throws IOException {
        List<M49DTO> m49DTOS = this.m49Service.readM49MethodologyAsJson();
        assertThat(m49DTOS).isNotNull();

        M49DTO m49France = m49DTOS.stream()
                .filter(m49DTO -> "France".equals(m49DTO.getCountryOrArea()))
                .findFirst()
                .orElse(null);
        assertThat(m49France).isNotNull();
    }

}
