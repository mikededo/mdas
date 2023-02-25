package mddg.selenium.pages;

import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResultsPage extends PageObjectBase {

  @FindBy(xpath = "//span[text()='07:00']")
  private WebElementFacade startTime;
  @FindBy(xpath = "//span[text()='08:25']")
  private WebElementFacade endTime;

  private void switchToResultsPage() {
    // Expect two tabs to exist
    waitFor(ExpectedConditions.numberOfWindowsToBe(2));

    // Switch to second tab
    WebDriver driver = getDriver();
    driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
  }

  public void getFirstAvailableFlight() {
    switchToResultsPage();
    waitFor(startTime);
    waitFor(endTime);
  }
}
