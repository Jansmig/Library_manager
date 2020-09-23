package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.domain.RentalDtoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public RentalDtoResponse mapToRentalDtoResponse (Rental rental) {
        return new RentalDtoResponse(
                rental.getId(),
                rental.getBook().getId(),
                rental.getBook().getOrigin().getTitle(),
                rental.getUser().getId(),
                rental.getUser().getFirstName(),
                rental.getUser().getLastName(),
                rental.getRentalDate(),
                rental.getReturnDate(),
                rental.isActive()
        );
    }

    public List<RentalDtoResponse> mapToRentalDtoResponseList (List<Rental> rentals) {
        return rentals.stream()
                .map(this::mapToRentalDtoResponse)
                .collect(Collectors.toList());
    }



}
