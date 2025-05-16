package com.example.Blog_Application.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostResponse {
    private List<PostDto> content;
    int pageNumber;
    int pageSize;
    int totalElements;
    int totalPages;

    boolean lastPage;
}
