package mapper;

import dto.BookingDTO;
import model.requestModels.BookingDates;
import model.requestModels.BookingRequestBody;

public class CreateBookingMapper {

    public static BookingRequestBody map(BookingDTO bookingDTO){
        BookingRequestBody body = new BookingRequestBody();
        body.setAdditionalneeds(bookingDTO.getAdditionalneeds());
        body.setDepositpaid(bookingDTO.getDepositpaid());
        body.setFirstname(bookingDTO.getFirstname());
        body.setLastname(bookingDTO.getLastname());
        body.setTotalprice(bookingDTO.getTotalprice());
        BookingDates dates = new BookingDates();
        dates.setCheckin(bookingDTO.getCheckin());
        dates.setCheckout(bookingDTO.getCheckout());
        body.setBookingdates(dates);
        return body;
    }
}
