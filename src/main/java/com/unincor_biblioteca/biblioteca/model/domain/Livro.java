package com.unincor_biblioteca.biblioteca.model.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Título é obrigatório.")
    private String titulo;

    private String descricao;

    @NotNull(message = "Ano é obrigatório.")
    private Integer ano;

    @NotNull(message = "Número de páginas é obrigatório.")
    private Integer paginas;

    @NotBlank(message = "Status é obrigatório.")
    private String status; // ex: DISPONIVEL, EMPRESTADO

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;
}
