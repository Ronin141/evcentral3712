package de.rwth.idsg.steve.ocpp.ws;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.rwth.idsg.steve.utils.LogFileRetriever;

@Controller
// @RequestMapping("")
public class LogFilter {

	 @GetMapping("/log/charger/{id}")
	 public String filterLogMessages(@PathVariable("id") String chargeBoxId, Model model) {
        String filePath = LogFileRetriever.INSTANCE.getLogFilePathOrErrorMessage();
        List<String> filteredMessages = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                // Normalize line by removing all whitespace
                String normalizedLine = line.replaceAll("\\s+", "").toLowerCase();

                // Normalize search keywords
                String normalizedChargeBoxId = chargeBoxId.replaceAll("\\s+", "").toLowerCase();

                // Check if the normalized line contains the normalized search terms
                if (normalizedLine.contains("[info]") && normalizedLine.contains("ws.websocketlogger") &&
                        normalizedLine.contains(normalizedChargeBoxId) ) {
						// &&(normalizedLine.contains("sending:") || normalizedLine.contains("received:"))
                    
                    // Clean up the line by removing unwanted characters
                    String cleanedLine = line.replaceAll("[,\\\\]", ""); // Remove commas and backslashes
                    
                    filteredMessages.add(cleanedLine.trim()); // Add cleaned and trimmed lines to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add filtered messages to the model to make them available in the JSP
        model.addAttribute("filteredMessages", filteredMessages);

        // Return the name of the JSP page where the results will be displayed
        return "log-results"; // Ensure this matches the path of your JSP file
    }
}
