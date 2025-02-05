package com.hashar.Task_Management_System.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesDTO {
    @NotBlank
    private String roleName;
    private List<Integer> permissionIds;
}