package pl.training.cloud.departments.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
@Entity
@Data
public class Department {

    @GeneratedValue
    @Id
    private Long id;
    @NonNull
    private String name;

}
