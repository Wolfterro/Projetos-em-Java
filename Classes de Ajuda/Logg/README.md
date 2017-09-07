## Logg.java
#### Descrição:

##### Esta pequena classe possui a função de criar um arquivo de registro (log) e registrar diversas ocorrências durante a execução do programa ao chamar os métodos de escrita disponíveis.

##### É possível iniciar um objeto Logg de duas maneiras: Com nome de arquivo customizado ou com nome de arquivo padrão (MyLogg.txt).

##### Ao iniciar o objeto com nome de arquivo, é possível inserir como forma de caminho onde o arquivo de registro estará localizado. Mas certifique-se de ser um local existente e que o programa tenha permissão de escrita naquele local, caso contrário as mensagens não poderão ser escritas e você receberá uma mensagem de erro!

##### Caso inicie o objeto utilizando o nome padrão, será criado um arquivo (MyLogg.txt) na mesma pasta que o programa está sendo executado.

##### Veja os exemplos abaixo:
```Java
// Criando um registro com nome padrão
Logg l = new Logg();

// Criando um registro com nome customizado
Logg o = new Logg("CustomLogg.txt");

// Criando um registro com nome e caminho customizado
Logg g = new Logg("/home/user/CustomLogg.txt");
```

##### A classe possui duas maneiras de você inserir uma mensagem no arquivo de registro: Sem data e hora ou com data e hora inseridos no início da mensagem.
```Java
// Escrevendo no arquivo de log sem data e hora
public void writeLogg([String message]);

// Escrevendo no arquivo de log com data e hora
public void writeDatedLogg([String message]);
```

##### É possível também alterar a formatação da data e hora que serão inseridas ao invocar o método writeDatedLogg:
```Java
// Configurando o formato de data (padrão: HH:mm:ss | dd/MM/yyyy)
public void setDateFormat(String dateFormat);
```

##### Os padrões de data e hora permitidos são os mesmos da classe [SimpleDateFormat](https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html) e estão disponíveis na documentação oficial da classe.

#### Requerimentos:
 - Java 8

#### Exemplos de uso:
```Java
Logg l = new Logg("MeuLogg.txt");

l.writeLogg("Esta é uma mensagem sem data e hora!");
l.writeDatedLogg("Esta é uma mensagem com data e hora!");

l.setDateFormat("yyyy/MM/dd - HH:mm:ss");
l.writeDatedLogg("Esta é uma mensagem com data e hora formatadas!")
```

##### O arquivo de registro MeuLogg.txt, portanto, deverá estar da seguinte maneira:
```Text
Esta é uma mensagem sem data e hora!
[15:03:25 | 16/08/2017] Está é uma mensagem com data e hora!
[2017/08/16 - 15:03:25] Está é uma mensagem com data e hora formatadas!
```
