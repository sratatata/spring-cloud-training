package pl.training.cloud.users.service;

import org.springframework.stereotype.Service;
import pl.training.cloud.users.model.Department;

import java.util.Optional;

@Service
public class FeignDepartmentsService implements DepartmentsService {

    private FeignDepartmentsClient feignDepartmentsClient;

    public FeignDepartmentsService(FeignDepartmentsClient feignDepartmentsClient) {
        this.feignDepartmentsClient = feignDepartmentsClient;
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        Department department = feignDepartmentsClient.getDepartmentById(id);
        return department != null ? Optional.of(department) : Optional.empty();
    }

}
