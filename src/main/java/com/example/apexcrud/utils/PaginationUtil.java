package com.example.apexcrud.utils;

import com.example.apexcrud.dto.UserFilterRequestDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

    public static Pageable buildPageable(Integer page, Integer size, String sortBy, String sortOrder) {
        // Default values if parameters are null or empty
        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        String sortField = sortBy != null ? sortBy : "id";
        Sort.Direction sortDirection = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;

        // Construct Sort object for sorting
        Sort sort = Sort.by(sortDirection, sortField);

        // Construct Pageable object for pagination
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public static String getFiledValue(UserFilterRequestDTO filter) {
        List<String> values = filter.getValues();
        if (values != null && !values.isEmpty()) {
            //FKOU-3469 some bug related to npe, might be due to data corruption. this should act as a bandage fix for now.
            if (values.size() == 1 && values.get(0) != null) {
                return values.get(0).trim();
            }
        }
        return "";
    }
}
