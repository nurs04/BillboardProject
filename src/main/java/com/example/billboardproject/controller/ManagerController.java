package com.example.billboardproject.controller;

import com.example.billboardproject.model.Billboard;
import com.example.billboardproject.service.BillboardService;
import com.example.billboardproject.service.FileUploadService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Controller
@RequestMapping(value = "/admin")
public class ManagerController {

    @Value("${loadURL}")
    private String loadURL;

    @Autowired
    private BillboardService billboardService;

    @Autowired
    private FileUploadService fileUploadService;

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/main")
    public String adminPage() {
        return "manager";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/detailEditBillboard/{billboard_id}")
    public String detailEditBillboardPage(Model model,
                                          @PathVariable(name = "billboard_id") Long id) {
        Billboard billboard = billboardService.getBillboard(id);
        if (billboard != null) {
            model.addAttribute("billboard", billboard);
        }
        return "detailEditing";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/allBillboards")
    public String allBillboardsPage(Model model) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        String dateString = dateFormat.format(new Date());

        int currentMonth = Integer.parseInt(dateString);
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("billboards", billboardService.getAllBillboards());
        return "allBilboards";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/addNewBillboard")
    public String addNewBillboardPage() {
        return "newBillboards";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/deleteBillboard")
    public String deleteFolder(@RequestParam(name = "deleteBillboardId") Long deleteBillboardId) {
        billboardService.deleteBillboard(deleteBillboardId);
        return "redirect:/admin/allBillboards/";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/editBillboard")
    public String editBillboard(@RequestParam(name = "id") Long id,
                               @RequestParam(name = "location") String location,
                               @RequestParam(name = "size") String size,
                               @RequestParam(name = "isHasLightning") boolean isHasLightning,
                               @RequestParam(name = "price") double price,
                               @RequestParam(name = "billboard_url") MultipartFile file) {
        String city = "Almaty";
        String type = "one-sided";
        Billboard billboard = billboardService.getBillboard(id);
        billboard.setCity(city);
        billboard.setPrice(price);
        billboard.setType(type);
        billboard.setLocation(location);
        billboard.setSize(size);
        billboard.setHasLightning(isHasLightning);

        if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
            fileUploadService.uploadImg(file, billboard);
        }

        billboardService.updateBillboard(billboard);

        return "redirect:/admin/allBillboards/";

    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @PostMapping(value = "/addBillboard")
    public String addBillboard(@RequestParam(name = "location") String location,
                               @RequestParam(name = "size") String size,
                               @RequestParam(name = "isHasLightning") boolean isHasLightning,
                               @RequestParam(name = "price") double price,
                               @RequestParam(name = "billboard_url") MultipartFile file) {
        String city = "Almaty";
        String type = "one-sided";
        Billboard billboard = Billboard.builder()
                .location(location)
                .price(price)
                .isHasLightning(isHasLightning)
                .type(type)
                .size(size)
                .city(city)
                .build();

        billboardService.addBillboard(billboard);

        if (Objects.equals(file.getContentType(), "image/jpeg") || Objects.equals(file.getContentType(), "image/png")) {
            fileUploadService.uploadImg(file, billboard);
        }

        return "redirect:/admin/allBillboards/";

    }

    @PreAuthorize("hasAnyAuthority('MANAGER')")
    @GetMapping(value = "/getAva/{token}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getAva(@PathVariable(name = "token", required = false) String token) throws IOException {
        String pictureUrl = loadURL + "default.jpg";
        if(token != null) {
            pictureUrl = loadURL;
//            pictureUrl = loadURL + token + ".jpg";
        }
        InputStream in;

        try{
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }catch (Exception e) {
            pictureUrl = loadURL + "default.jpg";
            ClassPathResource resource = new ClassPathResource(pictureUrl);
            in = resource.getInputStream();
        }

        return IOUtils.toByteArray(in);
    }


}
