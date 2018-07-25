package com.wayne.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.entity.Parts;
import com.wayne.entity.Type;
import com.wayne.exception.NotFoundException;
import com.wayne.service.PartsService;
import com.wayne.service.TypeService;
import com.wayne.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LV
 * @date 2018/7/25
 */
@Controller
@RequestMapping("/type")
public class TypeController {
    private Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    private TypeService typeService;

    @Autowired
    private PartsService partsService;

    @GetMapping
    public String listPage(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNum,
                           String keys,
                           Model model){

        Map<String, Object> params = new HashMap<>();
        params.put("keys", keys);
        PageInfo<Type> page = typeService.findAllAndLimit(pageNum, params);
        model.addAttribute("page", page);
        return "type/list";
    }

    @PostMapping("/add")
    public String add(String addTypeName, RedirectAttributes attributes){
        Type type = typeService.findByName(addTypeName);

        if (type == null){
            type = new Type();
            type.setTypeName(addTypeName);
            typeService.save(type);
            attributes.addFlashAttribute("message", "新增成功!");
        } else {
            attributes.addFlashAttribute("message", "该类型已存在!");
        }
        return "redirect:/type";
    }

    @PostMapping("/edit")
    public String edit(Type type, RedirectAttributes attributes){
        Type type1 = typeService.findByName(type.getTypeName());
        if (type1 != null && !type1.getId().equals(type.getId())){
            attributes.addFlashAttribute("message", "该类型已存在!");
        } else {
            typeService.update(type);
            attributes.addFlashAttribute("message", "更新成功!");
        }
        return "redirect:/type";
    }

    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        Type type = typeService.findById(id);
        List<Parts> partsList = partsService.findByTypeId(id);

        if (type == null){
            throw new NotFoundException();
        } else if (partsList.size() != 0) {
            attributes.addFlashAttribute("message", "该类型下有产品,不能删除!");
        } else {
            logger.debug("删除类型 : {}", type);
            typeService.delete(id);
            attributes.addFlashAttribute("message", "删除成功!");
        }
        return "redirect:/type";
    }


}
