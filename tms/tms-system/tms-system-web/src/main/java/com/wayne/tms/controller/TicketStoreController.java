package com.wayne.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.wayne.tms.entity.TicketStore;
import com.wayne.tms.service.TicketStoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/9/2
 */
@Controller
@RequestMapping("/ticketstore")
public class TicketStoreController {

    @Reference(version = "1.0")
    private TicketStoreService ticketStoreService;

    @GetMapping
    public String ticketStoreList(Model model,
                                  @RequestParam(name = "p",required = false,defaultValue = "1") Integer pageNo,
                                  @RequestParam(required = false,defaultValue = "") String storeName,
                                  @RequestParam(required = false,defaultValue = "") String storeManager,
                                  @RequestParam(required = false,defaultValue = "") String storeTel) {
        Map<String, Object> params = new HashMap<>();
        params.put("storeName", storeName);
        params.put("storeManager", storeManager);
        params.put("storeTel", storeTel);
        PageInfo<TicketStore> pageInfo = ticketStoreService.findAllTicketStoreByParams(pageNo, params);
        model.addAttribute("pageInfo", pageInfo);
        return "store/home";
    }

    @GetMapping("/new")
    public String ticketStoreAddPage() {

        return "store/new";
    }

    @PostMapping("/new")
    public String ticketStoreAdd() {
        return "redirect:/store";
    }

    @GetMapping("/{id:\\d+}")
    public String ticketStoreEdit(@PathVariable Integer id) {

        return "store/edit";
    }

    @PostMapping("/edit")
    public String ticketStoreEdit(TicketStore ticketStore) {

        return "redirect:/store";
    }

}
