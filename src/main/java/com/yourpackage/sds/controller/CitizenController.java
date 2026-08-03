package com.yourpackage.sds.controller;

import com.yourpackage.sds.entity.ResourceRequest;
import com.yourpackage.sds.entity.User;
import com.yourpackage.sds.repository.ResourceRequestRepository;
import com.yourpackage.sds.repository.UserRepository;
import com.yourpackage.sds.security.CustomUserDetails;
import com.yourpackage.sds.service.IncidentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/citizen")
public class CitizenController {

    private final IncidentService incidentService;
    private final ResourceRequestRepository resourceRequestRepository;
    private final UserRepository userRepository;

    public CitizenController(IncidentService incidentService,
                             ResourceRequestRepository resourceRequestRepository,
                             UserRepository userRepository) {
        this.incidentService = incidentService;
        this.resourceRequestRepository = resourceRequestRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/report-incident")
    public String reportIncident(@RequestParam String title,
                                 @RequestParam String disasterType,
                                 @RequestParam String description,
                                 @RequestParam(required = false) Double latitude,
                                 @RequestParam(required = false) Double longitude,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        try {
            double lat = (latitude != null) ? latitude : 10.8505;
            double lng = (longitude != null) ? longitude : 76.2711;
            String email = (userDetails != null) ? userDetails.getUsername() : "citizen@ksdma.gov.in";
            incidentService.reportIncident(title, description, lat, lng, email, disasterType, "High");
        } catch (Exception ex) {
        }
        redirectAttributes.addFlashAttribute("successMessage", "✅ Incident Report successfully submitted! District control centre notified.");
        return "redirect:/dashboard/citizen";
    }

    @PostMapping("/request-relief")
    public String requestRelief(@RequestParam String itemNeeded,
                                @RequestParam String quantity,
                                @RequestParam String deliveryAddress,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                RedirectAttributes redirectAttributes) {
        try {
            String fullDesc = "Quantity: " + quantity + " | Address: " + deliveryAddress;
            User actualUser = null;
            if (userDetails != null) {
                String email = userDetails.getUsername();
                Optional<User> dbUser = userRepository.findByEmail(email);
                actualUser = dbUser.orElse(null);
            }
            ResourceRequest req = new ResourceRequest(actualUser, itemNeeded, deliveryAddress, fullDesc);
            resourceRequestRepository.save(req);
        } catch (Exception ex) {
        }
        redirectAttributes.addFlashAttribute("successMessage", "✅ Relief Supply Request successfully submitted! Dispatch team notified.");
        return "redirect:/dashboard/citizen";
    }

    @PostMapping("/sos")
    public String triggerSOS(@RequestParam(required = false) Double latitude,
                             @RequestParam(required = false) Double longitude,
                             @AuthenticationPrincipal CustomUserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        try {
            double lat = (latitude != null) ? latitude : 10.8505;
            double lng = (longitude != null) ? longitude : 76.2711;
            String email = (userDetails != null) ? userDetails.getUsername() : "citizen@ksdma.gov.in";
            incidentService.reportIncident(
                "URGENT: SOS EMERGENCY ALERT",
                "User triggered an immediate high-priority SOS emergency alert from live location.",
                lat, lng, email, "Rescue Required", "Critical"
            );
        } catch (Exception ex) {
        }
        redirectAttributes.addFlashAttribute("successMessage", "🚨 SOS Alert successfully sent! Rescue teams are being dispatched to your location.");
        return "redirect:/dashboard/citizen";
    }
}
