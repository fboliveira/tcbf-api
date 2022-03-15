/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcbf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alexandre
 */
public class ManipuladorArquivo {

    Charset utf8 = StandardCharsets.UTF_8;
    Times time;
    Times[] CopiaTimes = new Times[20];
    int dist;
    Rodada rodada;
    String caminho;
    Path path;

    public Times Preenche(int linha) throws IOException {

        caminho = "times.txt";//Lista de times, Suas cidades, Onde Estão
        path = Paths.get(caminho);
        int cont = 0;
        String Ltime = null;

        try ( BufferedReader buffer = Files.newBufferedReader(path, utf8)) {
            Ltime = buffer.readLine();
            while (cont != linha) {
                Ltime = buffer.readLine();//Lista de times

                cont++;
            }
            String clubes[] = Ltime.split(";");// Faz o split
            int id = parseInt(clubes[0]);//Pega a numeraçao dos times
            String Ntime = clubes[1];
            int casa = parseInt(clubes[2]);
            int estado = parseInt(clubes[3]);
            int tempo = parseInt(clubes[5]);
            int localizacao = parseInt(clubes[4]);
            int classico = parseInt(clubes[6]);

            time = new Times(id, Ntime, casa, estado, localizacao, tempo, classico);
            //    System.out.println(time.getNome_());

        } //Fim Try
        CopiaTimes[linha] = time;
        return time;

    }// Fim Preenche

    public Rodada PreencheRodada(int linha) {
        caminho = "jogos.txt";//Tabela
        path = Paths.get(caminho);
        String jogo = null;
        int cont = 0;
        int time1 = 0;
        int time2 = 0;

        BufferedReader read;
        try {
            read = Files.newBufferedReader(path, utf8);
            jogo = read.readLine();
            while (cont != linha) {
                jogo = read.readLine();//Lista de times
                cont++;
            }
            String times[] = jogo.split("x");
            //       System.out.println(jogos.get(i));
            time1 = parseInt(times[0]);
            time2 = parseInt(times[1]);

        } catch (IOException ex) {
            Logger.getLogger(ManipuladorArquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Tabela

        rodada = new Rodada(time1, time2);
        //    System.out.println(rodada.getCasa() + "x" + rodada.getFora());
        return rodada;
    }

    public int preencheDistancia(int linha) throws IOException {
        String caminho = "distancia.txt";//Distancias
        Path path = Paths.get(caminho);

        int cont = 0;
        String LDistancia = null;

        try ( BufferedReader buffer = Files.newBufferedReader(path, utf8)) {
            LDistancia = buffer.readLine();
            while (cont != linha) {
                LDistancia = buffer.readLine();//Lista de times

                cont++;
            }

            dist = parseInt(LDistancia);

        } //Fim Try

        return dist;

    }

    /* FBO */
    public int[] readFile(String fileName) throws Exception {
        File f = new File(fileName);
        if (!f.exists() && f.isDirectory()) {
            throw new Exception("Arquivo não existe: " + fileName);
        }

        String[] content = null;

        try ( BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content = line.split(",");
            }
        } catch (FileNotFoundException e) {
            //Some error logging
        }

        int teams[] = new int[content.length];
        
        for(int i = 0; i < content.length; ++i) {
            teams[i] = Integer.parseInt(content[i].trim());
        }
        
        return teams;

    }

}
