# Classes de Ajuda
## Pequenas classes em Java que poderão ajudar no dia-a-dia!

### GetJSON.java
#### Descrição:

##### Esta pequena classe possui a função de resgatar os arquivos .json de uma API pública qualquer, fornecida pelo usuário através de uma URL em formato String.

#### Requerimentos:
 - org.json

#### Exemplo de uso:

```Java
GetJSON g = new GetJSON("https://example.com/json");
JSONObject j = g.get();
```

### Contador.java
#### Descrição:

##### Esta pequena classe possui a função de criar um pequeno relógio que poderá ser utilizado como um contador de tempo de execução do programa ou de outras funções essenciais do programa, como o tempo de execução de um método ou loop ou o tempo entre um método e outro.

##### O contador poderá retornar dois tipos de dados utilizando dois métodos disponíveis:
```Java
// Retorna o tempo formatado em String
public String getTimeFormatted();

// Retorna o tempo (segundos) em int
public int getTime();
```

##### O contador irá funcionar em uma Thread separada e só irá parar ao invocar o seguinte método:
```Java
// Para o contador por completo
public void stopCounter();
```

#### Requerimentos:
 - Java 8

#### Exemplo de uso:

```Java
Contador d = new Contador();
d.start();

// --------------------------------
// Vários métodos e loops depois...
// --------------------------------

String tempoGasto = String.format("Tempo gasto: %s (%d segundos)!",
	d.getTimeFormatted(), d.getTime());
System.out.println(tempoGasto); // Tempo gasto: 00:00:32 (32 segundos)!

d.stopCounter();
```
