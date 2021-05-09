package br.com.juliancambraia.livro.service;

import br.com.juliancambraia.livro.model.Livro;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LivroServiceImpl implements LivroService {
    List<Livro> livros = new ArrayList<>();

    @Override
    public String addLivro(Livro livro) {
        String message = "";
        boolean status = livros.add(livro);
        if (status) {
            message = "Livro foi adicionado com sucesso na Biblioteca.";
        } else {
            message = "Livro não pode ser adicionado na Biblioteca. Devido a algum erro técnico. Por favor tente mais tarde";
        }
        return message;
    }

    @Override
    public List<Livro> retrieveLivroList() {
        return livros;
    }
}
