package fr.brgm.mapClient.monitoring;

import fr.brgm.mapClient.monitoring.dao.ServiceDAO;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.jdbc.Sql;

/**
 * TestSld class for {@link ServiceRepository}
 */
@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
        "classpath:service.sql"
})
public class ServiceRepositoryTest {

    @Autowired
    private ServiceRepository serviceRepository;

    /**
     * Testing findAll method
     */
    @Test
    public void whenFindAllServicesShouldreturnAllServices() {
        List<ServiceDAO> serviceDAOList = this.serviceRepository.findAll();
        Assertions.assertEquals(2, serviceDAOList.size());
        // Testing first element
        ServiceDAO first = serviceDAOList.stream().filter(s -> "Service 1".equals(s.getName())).findFirst().orElse(null);
        Assertions.assertNotNull(first);
        Assertions.assertEquals("Service 1", first.getName());
        Assertions.assertEquals((Float) 100f, first.getSla());
    }

    /**
     * Testing valid save method
     */
    @Test
    public void whenSaveServiceWithValidDataShouldReturnService() {
        ServiceDAO toSave = new ServiceDAO();
        toSave.setName("Service 3");
        toSave.setSla(80.80F);

        ServiceDAO saved = this.serviceRepository.save(toSave);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Service 3", saved.getName());
        Assertions.assertEquals((Float) 80.80f, saved.getSla());
    }

    /**
     * Testing save method with existing name
     * Should throw DataIntegrityViolationException
     */
    @Test
    public void whenSaveServiceWithExistingNameShouldUpdate() {
        ServiceDAO toSave = new ServiceDAO();
        toSave.setName("Service 1");
        toSave.setSla(0F);

        ServiceDAO saved = this.serviceRepository.save(toSave);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Service 1", saved.getName());
        Assertions.assertEquals((Float) 0f, saved.getSla());
    }

    /**
     * Testing save method without service name
     * Should throw JpaSystemException
     */
    @Test
    public void whenSaveServiceWithNameNullShouldThrowJpaSystemException() {
        ServiceDAO toSave = new ServiceDAO();
        toSave.setName(null);
        toSave.setSla(0F);

        Assertions.assertThrows(JpaSystemException.class, 
                () -> this.serviceRepository.save(toSave));
    }

    /**
     * Testing save method without service sla
     * Should throw DataIntegrityViolationException
     */
    @Test
    public void whenSaveServiceWithSlaNullShouldThrowDataIntegrityViolationException() {
        ServiceDAO toSave = new ServiceDAO();
        toSave.setName("ServiceDTO");
        toSave.setSla(null);

        Assertions.assertThrows(DataIntegrityViolationException.class, 
                () -> this.serviceRepository.save(toSave));
    }

}