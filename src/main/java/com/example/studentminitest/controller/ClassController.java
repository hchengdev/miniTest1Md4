package com.example.studentminitest.controller;

import com.example.studentminitest.model.Student;
import com.example.studentminitest.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.studentminitest.model.Class;

import java.util.List;


@Controller
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassService classService;

    @RequestMapping("")
    public ModelAndView index(@PageableDefault(value = 5) Pageable pageable) {
        Page<Class> classes = classService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/classList");
        modelAndView.addObject("class", classes);
        return modelAndView;
    }

    @DeleteMapping("/clear")
    public void clearClass() {
        classService.truncateClass();
    }
}
