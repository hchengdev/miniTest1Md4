package com.example.studentminitest.controller;


import com.example.studentminitest.model.Student;
import com.example.studentminitest.model.StudentForm;
import com.example.studentminitest.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;

    @Value("${file-upload}")
    private String upload;

    @GetMapping("")
    public String index(Model model) {
        List<Student> studentList = studentService.findAll();
        model.addAttribute("student", studentList);
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("students", new Student());
        return "/create";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute StudentForm studentForm) {
        MultipartFile file = studentForm.getImg();
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
        Student student = new Student(studentForm.getFirstname(), studentForm.getLastname(), studentForm.getDob(), studentForm.getAddress(), studentForm.getMark(), fileName);
        studentService.save(student);
        ModelAndView mav = new ModelAndView("redirect:/students");
        mav.addObject("message", "Student added successfully");
        return mav;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "/delete";
    }

    @PostMapping("/delete")
    public String delete(Student student, RedirectAttributes redirect) {
        studentService.remove(student.getId());
        redirect.addFlashAttribute("message", "Student deleted successfully");
        return "redirect:/students";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id, Model model) {
        Student student = studentService.findById(id);
        model.addAttribute("student", student);
        return "/edit";
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute StudentForm studentForm) {
        Student existingStudent = studentService.findById(studentForm.getId());

        MultipartFile file = studentForm.getImg();
        String fileName = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {
                FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            existingStudent.setImg(fileName);
        }

        existingStudent.setFirstname(studentForm.getFirstname());
        existingStudent.setLastname(studentForm.getLastname());
        existingStudent.setDob(studentForm.getDob());
        existingStudent.setAddress(studentForm.getAddress());
        existingStudent.setMark(studentForm.getMark());

        studentService.save(existingStudent);
        ModelAndView mav = new ModelAndView("redirect:/students");
        mav.addObject("message", "Student updated successfully");
        return mav;
    }
}
