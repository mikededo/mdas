package mddg.selenium.pages;

import static net.thucydides.core.pages.components.HtmlTable.inTable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import mddg.selenium.domain.ReservationDTO;
import net.serenitybdd.core.pages.WebElementFacade;

public class ResultsPage extends PageObjectBase {

    private static final String ORIGIN_KEY = "origin";
    private static final String DESTINATION_KEY = "destination";
    private static final String OUTBOUND_SPAN_KEY = "outboundSpan";
    private static final String RETURN_SPAN_KEY = "returnSpan";
    private static final String PASSENGERS_KEY = "passengers";

    private static ReservationPage flightPage;

    private WebElementFacade tableList;

    public static void registerFlight(ReservationDTO reservation) {
        flightPage.registerFlight(reservation);
    }

    public List<ReservationDTO> getReservationList() {
        return inTable(tableList)
            .getRows()
            .stream()
            .map(this::mapReservation)
            .collect(Collectors.toList());
    }

    private ReservationDTO mapReservation(Map<Object, String> row) {
        return ReservationDTO.builder()
            .origin(row.get(ORIGIN_KEY))
            .destination(row.get(DESTINATION_KEY))
            .outboundDate(row.get(OUTBOUND_SPAN_KEY))
            .returnDate(row.get(RETURN_SPAN_KEY))
            .passengers(Integer.parseInt(row.get(PASSENGERS_KEY)))
            .build();
    }
}
