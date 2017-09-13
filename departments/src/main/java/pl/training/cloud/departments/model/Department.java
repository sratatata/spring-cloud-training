package pl.training.cloud.departments.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "departments")
@Entity
@Data
public class Department {

    @GeneratedValue
    @Id
    private Long id;
    private String name;

}
