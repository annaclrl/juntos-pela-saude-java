package br.com.fiap.view;

import br.com.fiap.model.Menu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        new Menu().exibirMenu();
    }
}