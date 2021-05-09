package br.com.juliancambraia.biblioteca.controller;

import br.com.juliancambraia.biblioteca.model.Livro;
import br.com.juliancambraia.biblioteca.service.BibliotecaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/biblioteca")
public class BibliotecaController {

    private BibliotecaService bibliotecaService;

    public BibliotecaController(BibliotecaService bibliotecaService) {
        this.bibliotecaService = bibliotecaService;
    }

    @PostMapping
    public String addLivro(@RequestBody Livro livro) {
        return bibliotecaService.addLivro(livro);
    }

    @PostMapping(value = "/ratelimit")
    public String addBookwithRateLimit(@RequestBody Livro livro) {
        return bibliotecaService.addLivroWithRateLimit(livro);
    }

    @GetMapping
    public List<Livro> getSellersList() {
        return bibliotecaService.getLivrosList();
    }

    @GetMapping("/bulkhead")
    public List<Livro> getSellersListBulkhead() {
        return bibliotecaService.getLivrosListBulkhead();
    }

}
