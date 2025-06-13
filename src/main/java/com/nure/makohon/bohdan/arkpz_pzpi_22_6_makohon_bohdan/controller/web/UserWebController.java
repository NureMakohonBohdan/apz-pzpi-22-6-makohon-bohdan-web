package com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.controller.web;

import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.dto.*;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.entity.User;
import com.nure.makohon.bohdan.arkpz_pzpi_22_6_makohon_bohdan.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/users")
@SessionAttributes("loggedInUser")
public class UserWebController {

    private final RestTemplate restTemplate;

    @Resource
    UserService userService;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    public UserWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // List All Users
    @GetMapping
    public String listUsers(Model model) {
        String url = apiBaseUrl + "/users";
        UserDTO[] users = restTemplate.getForObject(url, UserDTO[].class);
        model.addAttribute("users", users);
        return "users/list";
    }

    // View User Details
    @GetMapping("/view/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        String url = apiBaseUrl + "/users/" + id;
        UserDTO user = restTemplate.getForObject(url, UserDTO.class);
        model.addAttribute("user", user);
        return "users/view";
    }

    // Create User Form
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "users/create";
    }

    // Handle User Creation
    @PostMapping
    public String createUser(@ModelAttribute UserDTO user) {
        String url = apiBaseUrl + "/users";
        restTemplate.postForObject(url, user, UserDTO.class);
        return "redirect:/users";
    }

    // Edit User Form
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        String url = apiBaseUrl + "/users/" + id;
        UserDTO user = restTemplate.getForObject(url, UserDTO.class);
        model.addAttribute("user", user);
        return "users/edit";
    }

    // Handle User Update
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @ModelAttribute UserDTO user) {
        String url = apiBaseUrl + "/users/" + id;
        restTemplate.put(url, user);
        return "redirect:/users";
    }

    // Delete User
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        String url = apiBaseUrl + "/users/" + id;
        restTemplate.delete(url);
        return "redirect:/users";
    }

    // Display Login Page
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequestDTO());
        return "users/login";
    }

    // Handle Login Request
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDTO loginRequest, Model model) {
        String url = apiBaseUrl + "/users/login";
        try {
            UserDTO loggedInUser = restTemplate.postForObject(url, loginRequest, UserDTO.class);
            model.addAttribute("loggedInUser", loggedInUser);
            return "redirect:/users/dashboard";
        } catch (Exception e) {
            model.addAttribute("loginRequest", loginRequest);
            model.addAttribute("error", "Invalid email or password");
            return "users/login";
        }
    }

    // Signup Page
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "users/signup";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO, Model model) {
        try {
            String url = apiBaseUrl + "/users";
            restTemplate.postForObject(url, userDTO, UserDTO.class); // Forward DTO to REST API
            return "redirect:/users/login";
        } catch (IllegalArgumentException e) {
            // Display a user-friendly error message
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", userDTO); // Retain entered data for correction
            return "users/signup";
        } catch (Exception e) {
            // Fallback for unexpected errors
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            model.addAttribute("user", userDTO);
            return "users/signup";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(SessionStatus status) {
        status.setComplete();
        return "redirect:/users/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(@SessionAttribute("loggedInUser") UserDTO loggedInUser, Model model) {
        String sensorsUrl = apiBaseUrl + "/sensors";
        String notificationsUrl = apiBaseUrl + "/notifications/user/{userId}";

        // Fetch sensors and notifications via REST API
        SensorDTO[] sensors = restTemplate.getForObject(sensorsUrl, SensorDTO[].class);
        NotificationDTO[] notifications = restTemplate.getForObject(notificationsUrl, NotificationDTO[].class, loggedInUser.getId());

        // Add data to the model
        model.addAttribute("sensors", sensors);
        model.addAttribute("notifications", notifications);
        return "dashboard";
    }

    // Display User Settings Page
    @GetMapping("/settings")
    public String showUserSettingsPage(@SessionAttribute("loggedInUser") UserDTO loggedInUser, Model model) {
        try {
            String url = apiBaseUrl + "/users/{id}/settings";
            UserSettingDTO userSettings = restTemplate.getForObject(url, UserSettingDTO.class, loggedInUser.getId());
            model.addAttribute("userSettings", userSettings);
            return "user-settings"; // Render the user settings page
        } catch (Exception e) {
            model.addAttribute("error", "Failed to load user settings. Please try again.");
            return "redirect:/dashboard";
        }
    }






}
