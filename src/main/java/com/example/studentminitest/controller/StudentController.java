package com.example.studentminitest.controller;


import com.example.studentminitest.model.Student;
import com.example.studentminitest.model.StudentForm;
import com.example.studentminitest.service.IStudentService;
import com.example.studentminitest.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @Value("${file-upload}")
    private String upload;

    @ModelAttribute("student")
    public Iterable<Student> manufacturers() {return studentService.findAll();}

    @GetMapping("")
    public ModelAndView index(@PageableDefault(value = 5) Pageable pageable) {
        Page<Student> studentList = studentService.findAll(pageable);
        ModelAndView modelAndView = new ModelAndView("/index");
        modelAndView.addObject("student", studentList);
        return modelAndView;
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
    public String delete(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).get();
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
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        return "/view";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id).get();
        model.addAttribute("student", student);
        return "/edit";
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute StudentForm studentForm) {
        Student existingStudent = studentService.findById(studentForm.getId()).get();

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

    @DeleteMapping("/clear")
    public void clearStudent() {
        studentService.truncateStudent();
    }
}
