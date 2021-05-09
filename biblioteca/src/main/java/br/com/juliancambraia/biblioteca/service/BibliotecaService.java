package br.com.juliancambraia.biblioteca.service;

import br.com.juliancambraia.biblioteca.model.Livro;

import java.util.List;

public interface BibliotecaService {

    String addLivro(Livro livro);

    String addLivroWithRateLimit(Livro livro);

    List<Livro> getLivrosList();

    List<Livro> getLivrosListBulkhead();
}
