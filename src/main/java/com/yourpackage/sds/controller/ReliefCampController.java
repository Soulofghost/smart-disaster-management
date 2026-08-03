package com.yourpackage.sds.controller;

import com.yourpackage.sds.entity.ReliefCamp;
import com.yourpackage.sds.service.ReliefCampService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/camps")
public class ReliefCampController {

    private final ReliefCampService reliefCampService;

    public ReliefCampController(ReliefCampService reliefCampService) {
        this.reliefCampService = reliefCampService;
    }

    @GetMapping({ "", "/", "/map" })
    public String showCampsMap(Model model) {
        model.addAttribute("camps", reliefCampService.getAllCamps());
        return "camps-map";
    }

    @GetMapping("/api/all")
    @ResponseBody
    public List<ReliefCamp> getAllCampsApi() {
        return reliefCampService.getAllCamps();
    }
}
