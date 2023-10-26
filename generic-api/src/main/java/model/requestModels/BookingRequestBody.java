package model.requestModels;

import lombok.Data;

@Data
public class BookingRequestBody {
    private String firstname;
    private String lastname;
    private Integer totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additionalneeds;
}
