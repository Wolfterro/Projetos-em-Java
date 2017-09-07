## GetJSON.java
#### Descrição:

##### Esta pequena classe possui a função de resgatar os arquivos .json de uma API pública qualquer, fornecida pelo usuário através de uma URL em formato String.

#### Requerimentos:
 - org.json

#### Exemplo de uso:

```Java
GetJSON g = new GetJSON("https://example.com/json");
JSONObject j = g.get();
```