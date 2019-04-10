package com.realpacific.springclouddemo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel(description = "Details About User.")
public class User {
    private Integer id;

    @Size(min = 2, message = "Name should be at least 2 chars long.")
    @ApiModelProperty(notes = "name.size() >= 2")
    private String name;

    @Past(message = "Birth day can't be in past")
    @ApiModelProperty(notes = "Birth day should be in past")
    private Date birthDate;

    public User() {
    }

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
