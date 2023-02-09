package mddg.selenium.pages;

import java.util.*;
import mddg.selenium.domain.ReservationDTO;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.FindBy;

public class ReservationPage extends PageObjectBase {

    private WebElementFacade originInput; // <input id="originInput" ...>
    private WebElementFacade destinationInput; // <input id="destinationInput" ...>
    private WebElementFacade outboundDate; // <input id="outboundDate" ...>
    private WebElementFacade returnDate; // <input id="returnDate" ...>
    private WebElementFacade btnSubmitHomeSearcher; // <button id="btnSubmitHomeSearcher" ...>
    private WebElementFacade nextButtonCalendar;

    @FindBy(xpath = "//*[@aria-label='Passengers']")
    private WebElementFacade passengerField;
    @FindBy(xpath = "//*[@id='popup-list']")
    private WebElementFacade popupList;
    @FindBy(xpath = "//*[@for='oneWay']")
    private WebElementFacade oneWay;
    @FindBy(xpath = "//*[@data-month='5']")
    private List<WebElementFacade> dayElements;
    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElementFacade acceptCookies;

    public void registerFlight(ReservationDTO reservation) {
        insertOrigin(reservation);
        insertDestination(reservation);
        enableOneWayTrip(reservation);
        insertOutboundDate(reservation);
        submitSearch();
    }

    public void acceptCookies() {
        waitFor(acceptCookies);
        acceptCookies.click();
    }

    private void insertOrigin(ReservationDTO reservation) {
        typeInto(originInput, reservation.getOrigin());
        popupList.click();
    }

    private void insertDestination(ReservationDTO reservation) {
        waitFor(destinationInput);
        typeInto(destinationInput, reservation.getDestination());
        popupList.click();
    }

    private void enableOneWayTrip(ReservationDTO reservation) {
        if (Objects.nonNull(reservation.getReturnDate())) {
            // TBD
        } else {
            // Click the one way button if there's no return set
            oneWay.click();
        }
    }

    private void insertOutboundDate(ReservationDTO reservation) {
        calendarClickNextMonth(reservation);
        calendarClickDay(reservation);
    }

    private void submitSearch() {
        btnSubmitHomeSearcher.click();
    }

    private void calendarClickNextMonth(ReservationDTO reservation) {
        int month = Integer.parseInt(reservation.getOutboundDate().split("/")[1]);
        for (int i = 0; i < calculateMonthSkipsFromToday(month); i++) {
            nextButtonCalendar.click();
        }
    }

    private void calendarClickDay(ReservationDTO reservation) {
        int outboundDay = Integer.parseInt(reservation.getOutboundDate().split("/")[0]);
        for (WebElementFacade dayElement : dayElements) {
            if (dayElement.getText().equals(String.valueOf(outboundDay))) {
                dayElement.click();
                break;
            }
        }
    }

    private int calculateMonthSkipsFromToday(int outboundMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        // 3 months per page
        return outboundMonth - calendar.get(Calendar.MONTH);
    }
}
