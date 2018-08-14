package com.wayne.erp.controller;

import com.github.pagehelper.PageInfo;
import com.wayne.erp.controller.exceptionhandler.NotFoundException;
import com.wayne.erp.entity.Parts;
import com.wayne.erp.entity.Type;
import com.wayne.erp.service.PartsService;
import com.wayne.erp.service.TypeService;
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
 * @date 2018/7/23
 */
@Controller
@RequestMapping("/parts")
public class PartsController {

    private Logger logger = LoggerFactory.getLogger(PartsController.class);

    @Autowired
    private PartsService partsService;

    @Autowired
    private TypeService typeService;

    /**
     * 查询所有Parts对象并分页
     * @param pageNum
     * @param model
     * @return 跳转list页面
     */
    @GetMapping
    public String list(@RequestParam(name = "p", defaultValue = "1", required = false) Integer pageNum,
                       String partsName,
                       Integer partsType,
                       Integer partsInventory,
                       Model model){

        List<Type> typeList = typeService.findAll();
        model.addAttribute("typeList", typeList);

        Map<String, Object> params = new HashMap<>();
        params.put("partsType", partsType);
        params.put("partsName", partsName);
        params.put("partsInventory", partsInventory);

        PageInfo<Parts> page = partsService.findByParams(pageNum, params);


        model.addAttribute("page", page);
        return "parts/list";
    }



    /**
     * 根据请求路径中的id值，查找详情信息
     * @param id
     * @param model
     * @return 返回详情页面
     */
    @GetMapping("/{id:\\d+}")
    public String findById(@PathVariable Integer id, Model model){
        Parts parts = partsService.findById(id);
        if(parts == null){
            throw new NotFoundException();
        } else {
            model.addAttribute("parts", parts);
        }
        return "parts/detail";
    }

    /**
     * 根据id删除对象
     * @param id
     * @return 重定向到list页面
     */
    @GetMapping("/delete/{id:\\d+}")
    public String delete(@PathVariable Integer id, RedirectAttributes attributes){
        Parts parts = partsService.findById(id);
        if(parts != null){
            logger.debug("删除配件 : ", parts);
            partsService.deleteById(id);
        }
        attributes.addFlashAttribute("message", "删除成功!");
        return "redirect:/parts";
    }

    /**
     * 查询所有类型，并跳转新增页面
     * @param model
     * @return 请求转发新增页面
     */
    @GetMapping("/add")
    public String savePage(Model model){
        List<Type> typeList = typeService.findAll();
        model.addAttribute("typeList", typeList);

        return "parts/add";
    }

    /**
     * 接收新增页面提交的数据，并新增
     * @param parts
     * @return 重定向list页面
     */
    @PostMapping("/add")
    public String save(Parts parts, RedirectAttributes attributes){
        Parts parts1 = partsService.findByPartsNo(parts.getPartsNo());
        if(parts1 != null){
            partsService.update(parts);
        } else {
            partsService.save(parts);
        }

        attributes.addFlashAttribute("message", "保存成功!");
        return "redirect:/parts";
    }

    /**
     * 根据传入id查找对象，并跳转修改页面
     * @param id
     * @param model
     * @return 请求转发修改页面
     */
    @GetMapping("/edit/{id:\\d+}")
    public String editPage(@PathVariable Integer id, Model model){
        Parts parts = partsService.findById(id);
        List<Type> typeList = typeService.findAll();
        if(parts == null){
            throw new NotFoundException();
        }
        model.addAttribute("typeList", typeList);
        model.addAttribute("parts", parts);
        return "parts/edit";
    }

    /**
     * 根据传入参数，自动封装对象，并保存
     * @param parts
     * @param attributes
     * @return 重定向list页面
     */
    @PostMapping("/edit")
    public String edit(Parts parts, RedirectAttributes attributes){
        partsService.update(parts);
        attributes.addFlashAttribute("message", "修改成功");
        return "redirect:/parts";
    }

    @ResponseBody
    @PostMapping("/check")
    public Object check(String partsNo){
        Parts parts = partsService.findByPartsNo(partsNo);
        return parts;
    }


}
