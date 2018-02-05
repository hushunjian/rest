package com.m2m.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/rest/foo")
public class FooController extends BaseController {
    @SuppressWarnings("null")
    @ResponseBody
    @RequestMapping(value = "error", method = RequestMethod.GET)
    public Object test() {
        Integer i = null;
        i.toString();
        return "";
    }

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World")
                                   String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/foo")
    public String greeting(Model model) {
        return "foo";
    }

    @ResponseBody
    @RequestMapping("/getPages")
    public Object getPages(@RequestParam(value = "page", required = true, defaultValue = "0") int page) {
        List<Object> rtn = new ArrayList<>();
        for (int i = page; i < page + 5; i++) {
            Map<String, String> image = new HashMap<String, String>();
            image.put("id", String.format("img_%03d", i));
            image.put("src", String.format("image/img%03d.png", i % 5));
            rtn.add(image);
        }
        return rtn;
    }
}
