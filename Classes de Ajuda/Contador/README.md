## Contador.java
#### Descrição:

##### Esta pequena classe possui a função de criar um pequeno relógio que poderá ser utilizado como um contador de tempo de execução do programa ou de outras funções essenciais do programa, como o tempo de execução de um método ou loop ou o tempo entre um método e outro.

##### É possível iniciar um objeto Contador de duas maneiras: Sem tempo limite ou com tempo limite, em segundos.
##### Caso utilize um tempo limite estabelecido, o contador irá encerrar automaticamente quando chegar a marca estabelecida e não haverá necessidade de encerrá-lo manualmente.

##### Veja os exemplos abaixo:
```Java
// Contador sem tempo limite
Contador d = new Contador();
d.start();

// Contador com tempo limite (em segundos)
Contador c = new Contador(45);
c.start();
```

##### O contador poderá retornar três tipos de dados utilizando três métodos disponíveis:
```Java
// Retorna o tempo formatado em String
public String getTimeFormatted();

// Retorna o tempo (segundos) em int
public int getTime();

// Verifica se o contador ainda está rodando
public boolean isRunning();
```

##### O contador irá funcionar em uma Thread separada e só irá parar (caso não tenha inserido um tempo limite) ao invocar o seguinte método:
```Java
// Para o contador por completo
public void stopCounter();
```

#### Requerimentos:
 - Java 8

#### Exemplos de uso:

 - Sem tempo limite:
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

 - Com tempo limite
 ```Java
Contador c = new Contador(45);
c.start();

while(c.isRunning()) {
	String msg = String.format("\rTempo: %s", c.getTimeFormatted());
	
	System.out.print(msg);
	System.out.flush();
}
System.out.println("");
```