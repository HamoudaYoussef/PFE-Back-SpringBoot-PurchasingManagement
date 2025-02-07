package biz.picosoft.demo.controller;

import biz.picosoft.demo.domain.Categorie;
import biz.picosoft.demo.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategorieRessource {

    @Autowired
    private CategorieService categorieService;

    @GetMapping
    public List<Categorie> getCategories() {
        return categorieService.getAllCategories();
    }
}
