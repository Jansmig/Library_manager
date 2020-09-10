package com.project.LibraryManager.mapper;

import com.project.LibraryManager.domain.Rental;
import com.project.LibraryManager.domain.RentalDtoResponse;
import org.springframework.stereotype.Component;

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



}
