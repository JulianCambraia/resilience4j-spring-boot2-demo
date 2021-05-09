package br.com.juliancambraia.livro.model;

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
