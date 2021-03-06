package com.wang.course.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@Entity
@Cacheable
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 64)

    @NotBlank(message = "first name is mandatory")
    private String firstName;

    @NotNull
    @Size(max = 64)
    @NotBlank(message = "last name is mandatory")
    private String lastName;

    @OneToMany(mappedBy = "course")
    Set<Registration> registrations;

    @Column(name = "isActive", nullable = false)
    private boolean isActive = true;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
