package com.example.apexcrud.dto;

import com.example.apexcrud.enums.RoleType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RoleListDto {
    List<RoleType> roleTypeList;
}
