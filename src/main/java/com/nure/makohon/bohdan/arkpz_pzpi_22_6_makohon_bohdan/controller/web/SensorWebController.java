package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller.web;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.SensorDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.SensorDataDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.UserDTO;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.UserSetting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/sensors")
@SessionAttributes("loggedInUser")
public class SensorWebController {

    @Value("${api.base-url}")
    private String apiBaseUrl;

    private final RestTemplate restTemplate;

    public SensorWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Display Add Sensor Page
    @GetMapping("/add")
    public String showAddSensorPage(Model model) {
        model.addAttribute("sensorDTO", new SensorDTO());
        return "add-sensor";
    }

    // Handle Add Sensor Form Submission
    @PostMapping("/add")
    public String addSensor(
            @SessionAttribute("loggedInUser") UserDTO loggedInUser,
            @ModelAttribute SensorDTO sensorDTO,
            Model model) {
        try {
            String url = apiBaseUrl + "/sensors/user/{userId}";
            restTemplate.postForObject(url, sensorDTO, SensorDTO.class, loggedInUser.getId());
            return "redirect:/users/dashboard"; // Redirect back to the dashboard
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add sensor. Please try again.");
            model.addAttribute("sensorDTO", sensorDTO);
            return "add-sensor";
        }
    }
    // Handle Delete Sensor Request
    @GetMapping("/delete/{id}")
    public String deleteSensor(
            @PathVariable Integer id,
            Model model) {
        try {
            String url = apiBaseUrl + "/sensors/{id}";
            restTemplate.delete(url, id); // Call the REST API to delete the sensor
            return "redirect:/users/dashboard"; // Redirect back to the dashboard after deletion
        } catch (Exception e) {
            model.addAttribute("error", "Failed to delete sensor. Please try again.");
            return "dashboard";
        }
    }
    // Display Edit Sensor Page
    @GetMapping("/edit/{id}")
    public String showEditSensorPage(@PathVariable Integer id, Model model) {
        try {
            String url = apiBaseUrl + "/sensors/{id}";
            SensorDTO sensorDTO = restTemplate.getForObject(url, SensorDTO.class, id);
            model.addAttribute("sensorDTO", sensorDTO);
            return "edit-sensor"; // Render the edit sensor page
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load sensor. Please try again.");
            return "redirect:/users/dashboard";
        }
    }

    // Handle Edit Sensor Form Submission
    @PostMapping("/edit/{id}")
    public String editSensor(
            @PathVariable Integer id,
            @ModelAttribute SensorDTO sensorDTO,
            Model model) {
        try {
            String url = apiBaseUrl + "/sensors/{id}";
            restTemplate.put(url, sensorDTO, id); // Call the REST API to update the sensor
            return "redirect:/users/dashboard"; // Redirect back to the dashboard
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update sensor. Please try again.");
            model.addAttribute("sensorDTO", sensorDTO);
            return "edit-sensor";
        }
    }

    // Display Sensor Details Page with Data
    @GetMapping("/view/{id}")
    public String viewSensor(@PathVariable Integer id, Model model) {
        try {
            // Fetch sensor details
            String sensorUrl = apiBaseUrl + "/sensors/{id}";
            SensorDTO sensorDTO = restTemplate.getForObject(sensorUrl, SensorDTO.class, id);

            // Fetch sensor data
            String dataUrl = apiBaseUrl + "/sensors/{id}/data";
            List<SensorDataDTO> sensorData = List.of(restTemplate.getForObject(dataUrl, SensorDataDTO[].class, id));

            model.addAttribute("sensorDTO", sensorDTO);
            model.addAttribute("sensorData", sensorData);
            return "view-sensor"; // Render the view sensor page
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load sensor details or data. Please try again.");
            return "redirect:/dashboard";
        }
    }

    // Other methods...

    // Redirect to User Settings Page
    @GetMapping("/settings/user/{id}")
    public String showUserSettings(@PathVariable Integer id, Model model) {
        try {
            String url = apiBaseUrl + "/settings/user/{id}";
            UserSetting userSetting = restTemplate.getForObject(url, UserSetting.class, id);
            model.addAttribute("userSetting", userSetting);
            return "user-settings";
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load user settings. Please try again.");
            return "redirect:/dashboard";
        }
    }

}

