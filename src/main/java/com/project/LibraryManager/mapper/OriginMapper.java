package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Origin;
import com.project.LibraryManager.domain.OriginDtoRequest;
import com.project.LibraryManager.domain.OriginDtoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OriginMapper {

    public OriginDtoResponse mapToOriginDtoResponse (Origin origin) {
        return new OriginDtoResponse(
                origin.getId(),
                origin.getTitle(),
                origin.getAuthor(),
                origin.getPublishedYear(),
                origin.getIsbn(),
                origin.getBooks().stream()
                        .map(n -> n.getId())
                        .collect(Collectors.toList())
        );
    }

    public List<OriginDtoResponse> mapToOriginDtoResponseList (List<Origin> originList) {
        return (ArrayList<OriginDtoResponse>) originList.stream()
                .map(this::mapToOriginDtoResponse)
                .collect(Collectors.toList());
    }


    public Origin mapToOrigin (OriginDtoRequest originDtoRequest) {
        return new Origin(
                originDtoRequest.getId(),
                originDtoRequest.getTitle(),
                originDtoRequest.getAuthor(),
                originDtoRequest.getPublishedYear(),
                originDtoRequest.getIsbn(),
                originDtoRequest.getBooks()
        );
    }


}
