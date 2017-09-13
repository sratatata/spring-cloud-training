package pl.training.cloud.users.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import pl.training.cloud.users.model.Department;

import java.util.List;
import java.util.Optional;

@Service
public class BalancedDepartmentService implements DepartmentsService {

    private static final String DEPARTMENTS_MICROSERVICE = "departments-microservice";
    private static final String RESOURCE_NAME = "/departments/";

    private DiscoveryClient discoveryClient;

    public BalancedDepartmentService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Cacheable(value = "departments", unless="#result == null")
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        Optional<String> serviceUri = getServiceUri(id);
        if (!serviceUri.isPresent()) {
            return Optional.empty();
        }
        return getDepartment(serviceUri.get());
    }

    private Optional<Department> getDepartment(String serviceUri) {
        try {
            return Optional.of(new RestTemplate().getForObject(serviceUri, Department.class));
        } catch (HttpClientErrorException ex) {
            return Optional.empty();
        }
    }

    private Optional<String> getServiceUri(Long id) {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(DEPARTMENTS_MICROSERVICE);
        if (serviceInstances.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(serviceInstances.get(0).getUri().toString() + RESOURCE_NAME + id);
    }

}
