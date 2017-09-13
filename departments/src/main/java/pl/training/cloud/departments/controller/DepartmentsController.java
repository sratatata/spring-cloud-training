package pl.training.cloud.departments.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.training.cloud.common.model.Mapper;
import pl.training.cloud.common.web.UriBuilder;
import pl.training.cloud.departments.dto.DepartmentDto;
import pl.training.cloud.departments.model.Department;
import pl.training.cloud.departments.service.DepartmentNotFoundException;
import pl.training.cloud.departments.service.DepartmentsService;

import java.net.URI;
import java.util.Locale;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

@Api(description = "Departments resource")
@RequestMapping("departments")
@RestController
public class DepartmentsController {

    private DepartmentsService departmentsService;
    private Mapper mapper;
    private UriBuilder uriBuilder = new UriBuilder();

    public DepartmentsController(DepartmentsService departmentsService, Mapper mapper) {
        this.departmentsService = departmentsService;
        this.mapper = mapper;
    }

    @ApiOperation("Create new department")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createDepartment(@RequestBody DepartmentDto departmentDto) {
        Department department = mapper.map(departmentDto, Department.class);
        long departmentId = departmentsService.addDepartment(department).getId();
        URI newDepartmentUri = uriBuilder.requestUriWithId(departmentId);
        return created(newDepartmentUri).build();
    }

    @ApiOperation(value = "Get department by id", response = DepartmentDto.class)
    @RequestMapping(value = "{department-id}", method = RequestMethod.GET)
    public DepartmentDto getDepartmentById(@PathVariable("department-id") Long id) {
        Department department = departmentsService.getDepartmentById(id);
        return mapper.map(department, DepartmentDto.class);
    }


    @ApiOperation(value = "Update department")
    @RequestMapping(value = "{department-id}", method = RequestMethod.PUT)
    public ResponseEntity updateDepartment(@PathVariable("department-id") Long id,
                                           @RequestBody DepartmentDto departmentDto) {
        Department department = mapper.map(departmentDto, Department.class);
        department.setId(id);
        departmentsService.updateDepartment(department);
        return noContent().build();
    }

    @ApiOperation(value = "Delete department")
    @RequestMapping(value = "{department-id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDepartment(@PathVariable("department-id") Long id) {
        departmentsService.deleteDepartmentWithId(id);
        return noContent().build();
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity onOrganizationNotFound(DepartmentNotFoundException ex, Locale locale) {
        return new ResponseEntity<>(mapper.map(ex, locale), HttpStatus.NOT_FOUND);
    }

}
