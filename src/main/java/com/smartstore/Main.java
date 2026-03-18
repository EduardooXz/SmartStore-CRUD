package com.smartstore;

import com.smartstore.menu.MenuPrincipal;
import com.smartstore.service.CategoriaService;

public class Main {
    public static void main(String[] args) {
        //Inicializa as categorias padrão ao iniciar aplicação
        CategoriaService.cadastrarCategoria();
        MenuPrincipal.selecionarOptions();
    }
}