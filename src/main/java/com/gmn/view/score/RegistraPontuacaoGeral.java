package com.gmn.view.score;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO: CRIAR UMA CLASSE PARA SALVAR O SCORE DO JOGADOR QUANDO ELE SAIR DE JOGO EM ANDAMENTO, OU QUANDO ELE PERDER
 * */
public class RegistraPontuacaoGeral {

    private static final String diretorioBase = System.getProperty("user.dir");
    private static final String srcFolder = "save";
    private static final String fileName = "save.txt";

    private static final String caminhoCompleto = diretorioBase + File.separator + srcFolder;
    private static final String caminhoCompletoArquivo = diretorioBase + File.separator + srcFolder + File.separator + fileName;
    private static final DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    protected static void criaDiretorioSave(){

        try {
            File diretorio = new File(caminhoCompleto);
            if (!diretorio.exists()) {
                if (!diretorio.mkdirs()) {
                    JOptionPane.showMessageDialog(null, "ERRO AO CRIAR DIRETÓRIO DE SAVE!");
                }
            }
        }
        catch (Exception e){
            System.out.println("Erro ao criar diretório!");
            e.printStackTrace();
        }
    }

    protected static void criaArquivoSave(){
        File arquivo = new File(caminhoCompletoArquivo);
        try {
            arquivo.createNewFile();
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void criaCaminhoSave(){
        criaDiretorioSave();
        criaArquivoSave();
    }

    public static void registraPontuacao(int scoreAtual){
        LocalDateTime dataAtual = LocalDateTime.now();

        LinkedList<ScoreVO> listaPontuacaoAtual = listaPontuacao();
        ScoreVO novoScore = new ScoreVO(scoreAtual, dataAtual);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoCompletoArquivo))) {
            if(listaPontuacaoAtual.isEmpty()){
                listaPontuacaoAtual.add(novoScore);
            }
            else{
                if(listaPontuacaoAtual.size() < 5){
                    listaPontuacaoAtual.add(novoScore);
                    listaPontuacaoAtual.sort(Comparator.comparingInt(ScoreVO::getScore).reversed());
                }
                else {
                    for(int i = 0; i < listaPontuacaoAtual.size(); i++){
                        if (scoreAtual >= listaPontuacaoAtual.get(i).getScore()) {
                            // Se o score atual for maior ou igual a um score na lista, insira na posição atual e remove o ultimo
                            listaPontuacaoAtual.add(i, novoScore);
                            listaPontuacaoAtual.removeLast();
                            break;
                        }
                    }
                }
            }

            for(ScoreVO scoreVOAtual : listaPontuacaoAtual){
                writer.write(scoreVOAtual.getScore() + ";" + scoreVOAtual.getData().format(formatoDataHora));
                writer.newLine();
            }


        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao tentar salvar as pontuações: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public static LinkedList<ScoreVO> listaPontuacao(){

        LinkedList<ScoreVO> scoreVOList = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoCompletoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 2) {
                    int score = Integer.parseInt(partes[0]);
                    String dataHoraTexto = partes[1];
                    LocalDateTime data = LocalDateTime.parse(dataHoraTexto, formatoDataHora);
                    ScoreVO scoreVO = new ScoreVO(score, data);
                    scoreVOList.add(scoreVO);
                }
            }

            return scoreVOList;
        } catch (IOException e) {
            System.out.println("Ocorreu um erro ao ler as pontuações: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
