package org.example;

import org.example.controller.BancoDeDados;
import org.example.model.Item;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Sistema de Farmácia");

        BancoDeDados banco = new BancoDeDados();
        ArrayList<Item> itens = banco.ler();

        banco.excluir(1,itens);
    }
}