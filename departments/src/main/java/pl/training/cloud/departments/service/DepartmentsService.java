package pl.training.cloud.departments.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import pl.training.cloud.departments.model.Department;
import pl.training.cloud.departments.repository.DepartmentsRepository;
import sun.awt.windows.ThemeReader;

import java.util.Random;

@Service
public class DepartmentsService {

    private DepartmentsRepository departmentsRepository;

    public DepartmentsService(DepartmentsRepository departmentsRepository) {
        this.departmentsRepository = departmentsRepository;
    }

    public Department addDepartment(Department department) {
        departmentsRepository.saveAndFlush(department);
        return department;
    }

    @HystrixCommand(
            fallbackMethod = "getDepartmentByIdFallback"
            //commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000")
    )
    public Department getDepartmentById(Long id) {
        randomDelay();
        return departmentsRepository.getById(id)
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public Department getDepartmentByIdFallback(Long id) {
        return new Department("MAIN");
    }

    public void updateDepartment(Department department) {
        getDepartmentById(department.getId());
        departmentsRepository.saveAndFlush(department);
    }

    public void deleteDepartmentWithId(Long id) {
        Department department = getDepartmentById(id);
        departmentsRepository.delete(department);
    }

    private void randomDelay() {
        Random random = new Random();
        if (random.nextInt(3) == 2) {
            try {
                Thread.sleep(random.nextInt(11_000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
