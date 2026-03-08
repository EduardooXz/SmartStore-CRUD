package com.smartstore;

import com.smartstore.menu.MenuPrincipal;
import com.smartstore.service.CategoriaManager;

public class Main {
    public static void main(String[] args) {
        //Inicializa as categorias padrão ao iniciar aplicação
        CategoriaManager.cadastrarCategoria();
        MenuPrincipal.selecionarOptions();
    }
}