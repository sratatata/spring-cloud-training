package pl.training.cloud.departments.service;

import org.springframework.stereotype.Service;
import pl.training.cloud.departments.model.Department;
import pl.training.cloud.departments.repository.DepartmentsRepository;

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

    public Department getDepartmentById(Long id) {
        return departmentsRepository.getById(id)
                .orElseThrow(DepartmentNotFoundException::new);
    }

    public void updateDepartment(Department department) {
        getDepartmentById(department.getId());
        departmentsRepository.saveAndFlush(department);
    }

    public void deleteDepartmentWithId(Long id) {
        Department department = getDepartmentById(id);
        departmentsRepository.delete(department);
    }

}
