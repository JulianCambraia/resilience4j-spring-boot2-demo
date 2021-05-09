package br.com.juliancambraia.livro.service;

import br.com.juliancambraia.livro.model.Livro;

import java.util.List;

public interface LivroService {

    String addLivro(Livro livro);

    List<Livro> retrieveLivroList();
}
