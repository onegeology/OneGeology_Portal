package fr.brgm.mapClient.monitoring;

import com.google.common.collect.Lists;
import fr.brgm.mapClient.config.properties.ApplicationProperties;
import fr.brgm.mapClient.monitoring.dao.ServiceDAO;
import fr.brgm.mapClient.monitoring.dto.request.ServiceRequestDTO;
import fr.brgm.mapClient.monitoring.dto.request.TemplateRequestDTO;
import fr.brgm.mapClient.monitoring.dto.response.ServiceResponseDTO;
import fr.brgm.mapClient.monitoring.dto.response.TemplateResponseDTO;
import fr.brgm.mapClient.monitoring.zabbixapiclient.ZabbixService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * TestSld class for {@link MonitoringService}
 */
@SpringBootTest
public class MonitoringServiceTest extends Mockito {

    /**
     * Service to test
     */
    @InjectMocks
    private MonitoringService monitoringService;

    /**
     * Mock zabbix service
     */
    @Mock
    private ZabbixService zabbixService;

    /**
     * Mock service repository
     */
    @Mock
    private ServiceRepository serviceRepository;

    /**
     * Properties
     */
    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * Mock a list of ServiceDAO
     */
    private List<ServiceDAO> createServiceDAOList() {
        ServiceDAO serviceDAO = new ServiceDAO();
        serviceDAO.setName("Service 1");
        serviceDAO.setSla(95.20f);

        ServiceDAO serviceDAO2 = new ServiceDAO();
        serviceDAO2.setName("Service 2");
        serviceDAO2.setSla(95.22f);

        return Lists.newArrayList(serviceDAO, serviceDAO2);
    }

    /**
     * Set up
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(this.monitoringService, "applicationProperties", this.applicationProperties);
        ReflectionTestUtils.setField(this.monitoringService, "zabbixService", this.zabbixService);
        ReflectionTestUtils.setField(this.monitoringService, "serviceRepository", this.serviceRepository);

        doNothing().when(this.zabbixService).login(anyString(), anyString());
        when(this.zabbixService.getOrCreateItemId(eq("template"), anyString(), anyString(), any(Map.class), any(Map.class))).thenReturn(1L);
        when(this.zabbixService.getOrCreateItemId(eq("application"), anyString(), anyString(), any(Map.class), any(Map.class))).thenReturn(2L);
        when(this.zabbixService.getOrCreateItemId(eq("service"), anyString(), anyString(), any(Map.class), any(Map.class))).thenReturn(3L);
        when(this.zabbixService.getOrCreateItemId(eq("httptest"), anyString(), anyString(), any(Map.class), any(Map.class))).thenReturn(4L);
        when(this.zabbixService.getOrCreateItemId(eq("trigger"), anyString(), anyString(), any(Map.class), any(Map.class))).thenReturn(5L);
        when(this.zabbixService.getItemId(eq("trigger"), anyString(), any(Map.class))).thenReturn(6L);

        when(this.serviceRepository.findAll()).thenReturn(this.createServiceDAOList());
    }

    /**
     * Testing monitoring method with valid data
     */
    @Test
    public void whenMonitoringValidInputDataShouldReturnValidData() {
        ServiceRequestDTO serviceRequestDTO = new ServiceRequestDTO();
        serviceRequestDTO.setName("name");
        serviceRequestDTO.setScenarioName("scenarioName");
        serviceRequestDTO.setStepName("stepName");
        serviceRequestDTO.setStepStatusCode("200");
        serviceRequestDTO.setTriggerName("triggerName");
        serviceRequestDTO.setUrl("url");

        TemplateRequestDTO templateRequestDTO = new TemplateRequestDTO();
        templateRequestDTO.setApplicationName("applicationName");
        templateRequestDTO.setGroupId(15L);
        templateRequestDTO.setHostId(10084L);
        templateRequestDTO.setTemplateName("templateName");
        templateRequestDTO.setServices(Collections.singletonList(serviceRequestDTO));

        TemplateResponseDTO templateResponseDTO = this.monitoringService.monitoring(templateRequestDTO);
        Assertions.assertNotNull(templateResponseDTO);
        Assertions.assertEquals(Long.valueOf(1), templateResponseDTO.getTemplateId());
        Assertions.assertEquals(Long.valueOf(2), templateResponseDTO.getApplicationId());
        Assertions.assertEquals(Long.valueOf(3), templateResponseDTO.getServiceClusterId());

        ServiceResponseDTO serviceResponseDTO = templateResponseDTO.getServices().get(0);
        Assertions.assertEquals(Long.valueOf(3), serviceResponseDTO.getServiceId());
        Assertions.assertEquals(Long.valueOf(4), serviceResponseDTO.getScenarioId());
        Assertions.assertEquals(Long.valueOf(6), serviceResponseDTO.getTriggerId());
    }

    /**
     * Testing monitoringService.getAllSLAs
     * It should return all the services with their SLA
     */
    @Test
    public void whenGetAllSLAsShouldreturnAllServiceswithTheirSLAs() {
        List<fr.brgm.mapClient.monitoring.dto.ServiceDTO> serviceDTOS = this.monitoringService.getAllSLAs();
        Assertions.assertNotNull(serviceDTOS);
        Assertions.assertEquals(2, serviceDTOS.size());

        fr.brgm.mapClient.monitoring.dto.ServiceDTO first = serviceDTOS.stream().filter(s -> "Service 1".equals(s.getName())).findFirst().orElse(null);
        Assertions.assertNotNull(first);
        Assertions.assertEquals("Service 1", first.getName());
        Assertions.assertEquals((Float) 95.2f, first.getSla());

        fr.brgm.mapClient.monitoring.dto.ServiceDTO second = serviceDTOS.stream().filter(s -> "Service 2".equals(s.getName())).findFirst().orElse(null);
        Assertions.assertNotNull(second);
        Assertions.assertEquals("Service 2", second.getName());
        Assertions.assertEquals((Float) 95.22f, second.getSla());
    }

}
