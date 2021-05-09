package br.com.juliancambraia.livro.controller;

import br.com.juliancambraia.livro.model.Livro;
import br.com.juliancambraia.livro.service.LivroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public String addLivro(@RequestBody Livro livro) {
        return livroService.addLivro(livro);
    }

    @GetMapping
    public List<Livro> retrieveLivroList() {
        return livroService.retrieveLivroList();
    }
}
