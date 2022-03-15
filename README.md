# TCBF API

Cálculo do custo de uma solução para o problema da Tabela do Campeonato Brasileiro. O projeto inicial foi desenvolvido no seguinte trabalho de conclusão de curso, utilizando o [*framework* jMetal, versão 4.3](https://github.com/jMetal/jMetal/releases/tag/v4.3).

- Título: [*Aplicação de uma meta-heurística para o problema de alocação de jogos do campeonato brasileiro de futebol.*](https://monografias.ufop.br/handle/35400000/391)
- Autor: *Maciel Filho, Alexandre José Teixeira*
- Orientadores: *Oliveira, Fernando Bernardes de; Alexandre, Rafael Frederico.*

Este repositório é uma adaptação do [código original](https://github.com/alexandremaciel1991/TB) para que receba uma sequência de times [0..n[ como entrada,  calcule a solução e retorne a tabela com os confrontos e o custo.

Por simplicidade, os dados de entrada são gravados em um arquivo texto com os valores separados por vírgula. Um exemplo está disponível no arquivo [data/solution.txt](./data/solution.txt). Este arquivo é passado como parâmetro por linha de comando.

## Como compilar

O projeto utiliza o [Apache Maven 3.6.3](https://maven.apache.org/index.html) como gerenciador.

```bash
Apache Maven 3.6.3
Maven home: /usr/share/maven
Java version: 11.0.14, vendor: Ubuntu, runtime: /usr/lib/jvm/java-11-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "5.13.0-35-generic", arch: "amd64", family: "unix"
```

Depois de clonar o projeto para o seu computador local, tendo o Java e o Maven instalados, execute o seguinte comando:

```bash
mvn install
```

Ao final, um arquivo JAR será gerado na pasta [/target](./target) com o nome [tcbf-api-0.1.jar](./target/tcbf-api-0.1.jar).

## Como utilizar

Para executar o código, copie o arquivo **tcbf-api-0.1.jar** e os arquivos de dados na pasta [data](./data): [distancia.txt](./data/distancia.txt), [jogos.txt](./data/jogos.txt) e [times.txt](./data/times.txt) para uma pasta qualquer.

Execute o arquivo pelo terminal passando como parâmetro o seu arquivo com a solução.

```bash
java -jar tcbf-api-0.1.jar solution.txt
```

## Trabalhos futuros

A proposta de continuidade desta API é transformá-la em um projeto Web, em que os dados possam ser submetidos via *post* e o cálculo retornado em formato *JSON* para o requisitante.
