package pl.training.cloud.users.service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import feign.FeignException;
import org.springframework.stereotype.Service;
import pl.training.cloud.users.model.Department;

import java.util.Optional;

@DefaultProperties(
        commandProperties = {
                @HystrixProperty(name="metrics.rollingStats.timeInMilliseconds", value="15000")
        }
)
@Service
public class FeignDepartmentsService implements DepartmentsService {

    private FeignDepartmentsClient feignDepartmentsClient;

    public FeignDepartmentsService(FeignDepartmentsClient feignDepartmentsClient) {
        this.feignDepartmentsClient = feignDepartmentsClient;
    }

    @HystrixCommand(
            threadPoolKey = "departmentsThreadPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize",value="10"),
                    @HystrixProperty(name="maxQueueSize", value="15")
            },
            commandProperties = {
                    @HystrixProperty(name="circuitBreaker.requestVolumeThreshold", value="10"),
                    @HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="75"),
                    @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value="7000"),
                    @HystrixProperty(name="metrics.rollingStats.numBuckets",value="5")
            }
    )
    // (requests per second at peak when the service is healthy * 99th percentile latency in seconds)
    // + small amount of extra threads for overhead
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        try {
            Department department = feignDepartmentsClient.getDepartmentById(id);
            return department != null ? Optional.of(department) : Optional.empty();
        } catch (FeignException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

}
