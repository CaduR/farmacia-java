package org.example.controller;

import org.example.model.Item;


import java.io.*;
import java.util.ArrayList;

public class BancoDeDados {
    private Item item;

    public BancoDeDados(){

    }

    public void cadastrar(Item item, boolean opcao){
        try{
            OutputStream os = new FileOutputStream("medicamentos.txt",opcao);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            String linha = item.getNome()+","+item.getQuantidade()+","+item.getTipo();

            bw.write(linha);
            bw.newLine();

            bw.close();
            osw.close();
            os.close();

            System.out.println("O medicamento "+item.getNome()+" foi cadastrado com sucesso!");
        }catch (Exception e){
            System.out.println("Não conseguiu cadastrar o medicamento");
            System.out.println(e);
        }
    }

    public void editar(int codigo, ArrayList<Item> itens){
        Item item = itens.get(codigo);
        itens.remove(codigo);
        item.setNome("Tilenol 200mg teste");
        item.setQuantidade(300);
        item.setTipo("Frasco 200ml");

        itens.add(codigo,item);

        for (int i=0; i<itens.size(); i++){
            if(i == 0) {
                cadastrar(itens.get(i), false);
            } else{
                cadastrar(itens.get(i),true);
            }
        }

    }

    public Item pesquisar(int codigo, ArrayList<Item> itens) {
        try {
            Item item = itens.get(codigo);
            return item;

        }catch (Exception e){
            return null;
        }
    }

    public void excluir(int codigo, ArrayList<Item> itens){
        itens.remove(codigo);

        for (int i=0; i<itens.size(); i++){
            if(i == 0) {
                cadastrar(itens.get(i), false);
            } else{
                cadastrar(itens.get(i),true);
            }
        }
    }

    public ArrayList<Item> ler() {
        ArrayList<Item> itens = new ArrayList<>(); // Inicializa aqui para retornar vazio em vez de null se der erro

        try {
            InputStream is = new FileInputStream("medicamentos.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String linha = br.readLine();
            ArrayList<String> linhas = new ArrayList<>();

            while(linha != null){
                // CORREÇÃO: Evita adicionar linhas vazias que quebram o split
                if(!linha.trim().isEmpty()) {
                    System.out.println(linha);
                    linhas.add(linha);
                }
                linha = br.readLine();
            }

            // Boa prática: fechar o leitor
            br.close();

            System.out.println("O arquivo medicamentos.txt foi lido com sucesso");

            for (String l : linhas){
                String[] elementos = l.split(",");

                // CORREÇÃO: Validação básica para evitar ArrayIndexOutOfBounds
                if(elementos.length >= 3) {
                    // .trim() remove espaços em branco acidentais que quebram o parseInt
                    int quantidade = Integer.parseInt(elementos[1].trim());
                    Item item = new Item(elementos[0].trim(), quantidade, elementos[2].trim());
                    itens.add(item);
                }
            }
            System.out.println("Linhas convertidas em objetos com sucesso");
            return itens;

        } catch (Exception e) {
            System.out.println("Erro ao ler/converter o arquivo:");
            e.printStackTrace(); // CORREÇÃO: Isso vai te mostrar ONDE está o erro real (ex: NumberFormatException)
            return new ArrayList<>(); // Retorna lista vazia para não quebrar a Main
        }
    }
}
