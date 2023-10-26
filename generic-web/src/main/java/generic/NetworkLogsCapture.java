package generic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v114.network.Network;
import org.openqa.selenium.edge.EdgeDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static config.factory.ConfigFactory.*;

public class NetworkLogsCapture {

    static String dateName = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());

    StringBuilder networkLogs = new StringBuilder();

    public void logNetworkFailure(WebDriver driver)  {

        if(getConfig().runModeBrowser().equalsIgnoreCase("local")) {
            DevTools devTool = null;

            int ignoreStatusCodeMin = getConfig().networkCallLogIgnoreCodeMin();
            int ignoreStatusCodeMax = getConfig().networkCallLogIgnoreCodeMax();

            String browserName = getConfig().browser().toUpperCase();
            if(browserName.equals("CHROME") || browserName.equals("EDGE")){

                switch (browserName) {
                    case "CHROME":
                        devTool = ((ChromeDriver) driver).getDevTools();
                        break;
                    case "EDGE":
                        devTool = ((EdgeDriver) driver).getDevTools();
                        break;
                }
                devTool.createSession();
                devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        /* // Request Listener
        devTool.addListener(Network.requestWillBeSent(), requestSent -> {
            Request request = requestSent.getRequest();
            logs.append("Request URL: "+ request.getUrl()+ "\n");
            logs.append("Request Method: " + request.getMethod().toString() + "\n");
            logs.append("------------------------------------------------------------\n");
                });
        */
                // Response Listener
                devTool.addListener(Network.responseReceived(), responseReceived -> {
                    int responseStatus = responseReceived.getResponse().getStatus();
                    if (responseStatus < ignoreStatusCodeMin || responseStatus > ignoreStatusCodeMax) {

                        networkLogs.append("Response URL: " + responseReceived.getResponse().getUrl() + "\n");
                        networkLogs.append("Response Type: " + responseReceived.getType().toString() + "\n");
                        networkLogs.append("Response Status: " + responseStatus + "\n");
                        networkLogs.append("Response Header: " + responseReceived.getResponse().getHeaders().toString() + "\n");
                        networkLogs.append("MIME Type: " + responseReceived.getResponse().getMimeType() + "\n");

                        networkLogs.append("****************************************************************\n");

//                    logs.append(responseReceived.getResponse().getUrl() + "\n");
//                    logs.append(responseReceived.getResponse().getStatus() + "\n");
//                    logs.append(responseReceived.getResponse().getHeaders().toString() + "\n");
//                    logs.append(responseReceived.getResponse().getMimeType() + "\n");
                    }
                });
            } // if browser name is matching
        } // if network log capture is enabled
    }

    public String getNetworkLogs() {
        return networkLogs.toString();
    }
}
