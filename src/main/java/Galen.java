import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Galen {

    @Test
    public void baseLineTest() throws Exception {


        WebDriver driver = BrowserFactory.getBrowser("chrome");
        driver.navigate().to("https://www.google.co.in");

        runGalen("testUI", driver, "desktop"); //change the first parameter to switch the run mode

    }


    public void runGalen(String action, WebDriver driver, String viewport) throws Exception {
        LayoutReport layoutReport;
        GalenTestInfo test = GalenTestInfo.fromString("Layout Tests");
        List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();;
        HtmlReportBuilder htmlreports = new HtmlReportBuilder();;

        Thread.sleep(1000);


        String specPath = "src/main/java/SampleSpecFile.spec";


        String pageDumpPath = "resources/screenshots/" + viewport + "/" + "SampleSpecFile" + ".dump";

        if (action.equals("takeDump"))
            com.galenframework.api.Galen.dumpPage(driver, "SampleSpecFile.spec", specPath, pageDumpPath);


        else if (action.equals("testUI")) {
            layoutReport = com.galenframework.api.Galen.checkLayout(driver, specPath, Arrays.asList(viewport));
            test.getReport().layout(layoutReport, "Layout tests on " + viewport + ", " + "SampleSpecFile.spec");

            String reportFolderPath = "resources/reports/galen-html-reports";
            tests.add(test);
            htmlreports.build(tests, reportFolderPath);
            tests.clear();
        }
    }
}


