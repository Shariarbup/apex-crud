package com.example.apexcrud.dto;

import com.example.apexcrud.model.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilterRequest {
    private static final long serialVersionUID = 1L;

    private List<UserFilterRequestDTO> filters;
}
