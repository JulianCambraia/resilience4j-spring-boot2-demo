package br.com.juliancambraia.biblioteca.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Livro {
    private long id;
    private String titulo;
    private String autor;
    private String status;

}
