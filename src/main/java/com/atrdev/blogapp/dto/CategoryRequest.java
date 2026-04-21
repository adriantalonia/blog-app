package com.atrdev.blogapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 50, message = "Category name must between {min} and {max} characters")
    @Pattern(regexp = "^[\\w\\s-]+$", message = "Category na,e can only contain letters, numbers, spaces and hyphens")
    private String name;

    private String description;
}
