package mddg.selenium.steps.search;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import javax.xml.transform.Result;
import mddg.selenium.domain.ReservationDTO;
import mddg.selenium.pages.ResultsPage;
import mddg.selenium.pages.ReservationPage;

public class SearchStep {

    private static final String WEB_ROOT = "https://www.vueling.com/en";

    final private ResultsPage resultsPage = new ResultsPage();
    final private ReservationPage reservationPage = new ReservationPage();

    @Given("^I'm in the main page$")
    public void imInTheMainPage()  {
        reservationPage.openAt(WEB_ROOT);
    }

    @Given("^I accept all the cookies$")
    public void iAcceptAllTheCookies() {
        reservationPage.acceptCookies();
    }

    @When("^I want to find a flight$")
    public void iWantToFindAFlight(List<ReservationDTO> list) {
       list.forEach(reservationPage::registerFlight);
    }

    @Then("^I get an available flight$")
    public void iGetAnAvailableFlight() {
        resultsPage.getFirstAvailableFlight();
    }
}
