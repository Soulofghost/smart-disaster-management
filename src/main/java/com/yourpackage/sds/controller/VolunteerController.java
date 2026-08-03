package com.yourpackage.sds.controller;

import com.yourpackage.sds.entity.Incident;
import com.yourpackage.sds.entity.Volunteer;
import com.yourpackage.sds.repository.IncidentRepository;
import com.yourpackage.sds.security.CustomUserDetails;
import com.yourpackage.sds.service.VolunteerService;
import com.yourpackage.sds.service.RescueMissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final RescueMissionService rescueMissionService;
    private final IncidentRepository incidentRepository;

    public VolunteerController(VolunteerService volunteerService, 
                               RescueMissionService rescueMissionService,
                               IncidentRepository incidentRepository) {
        this.volunteerService = volunteerService;
        this.rescueMissionService = rescueMissionService;
        this.incidentRepository = incidentRepository;
    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String registerAsVolunteer(@RequestParam String skills,
                                      @RequestParam String district,
                                      @RequestParam(required = false) Boolean hasVehicle,
                                      @RequestParam(required = false) String vehicleType,
                                      @RequestParam(required = false) String vehicleNumber,
                                      @AuthenticationPrincipal CustomUserDetails userDetails,
                                      RedirectAttributes redirectAttributes) {
        if (userDetails != null && userDetails.getUser() != null) {
            volunteerService.registerVolunteer(userDetails.getUser().getId(), skills, district, hasVehicle, vehicleType, vehicleNumber);
            redirectAttributes.addFlashAttribute("successMessage", "Successfully registered as a volunteer! Profile active.");
        }
        return "redirect:/dashboard/volunteer";
    }

    @PostMapping("/availability")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'ADMIN')")
    public String updateAvailability(@RequestParam String availability,
                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                     RedirectAttributes redirectAttributes) {
        if (userDetails != null && userDetails.getUser() != null) {
            Volunteer volunteer = volunteerService.getVolunteerByUserId(userDetails.getUser().getId());
            if (volunteer != null) {
                volunteerService.updateAvailability(volunteer.getId(), availability);
                redirectAttributes.addFlashAttribute("successMessage", "Readiness status updated to " + availability);
            }
        }
        return "redirect:/dashboard/volunteer";
    }

    @PostMapping("/mission/status")
    @PreAuthorize("hasAnyRole('VOLUNTEER', 'ADMIN')")
    public String updateMissionStatus(@RequestParam Long missionId,
                                      @RequestParam String status,
                                      RedirectAttributes redirectAttributes) {
        rescueMissionService.updateMissionStatus(missionId, status);
        if ("COMPLETED".equalsIgnoreCase(status)) {
            redirectAttributes.addFlashAttribute("successMessage", "🎉 Rescue Mission #" + missionId + " marked as COMPLETED! Great work.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Mission #" + missionId + " status updated to " + status);
        }
        return "redirect:/dashboard/volunteer";
    }
}
