## EDownloaderInfo.java
#### Descrição:

##### Esta pequena classe possui a função de resgatar as informações disponíveis nos álbuns do website e-hentai.
##### Esta classe permite, por exemplo, obter o endereço de todas as imagens disponíveis no álbum, o nome do álbum, o idioma do álbum, se foi traduzido ou não, o uploader, a data do upload, o tamanho do álbum e a quantidade de imagens disponíveis no álbum. Com isso, será possível baixar as imagens utilizando os endereços resgatados.
##### Para isso, a classe utiliza a biblioteca [org.jsoup](https://github.com/jhy/jsoup) para obter estas informações diretamente do álbum.

##### É possível inicializar o objeto EDownloaderInfo utilizando apenas a URL do álbum em String da seguinte maneira:
```Java
EDownloaderInfo e = new EDownloaderInfo(String albumURL);
```

##### Após inicializar o objeto com a URL do álbum desejado, basta chamar o método getInfo() para iniciar o processo de resgate de valores:
```Java
EDownloaderInfo e = new EDownloaderInfo(url);
e.getInfo();
```

##### Dependendo do álbum escolhido, este processo pode levar um tempo, deixando a Thread principal ocupada. É recomendável que você utilize esta classe em uma Thread separada, especialmente se estiver utilizando alguma interface gráfica ou integrando diretamente em um aplicativo Android.

##### Se o processo for bem sucedido, será possível obter os valores resgatados utilizando alguns métodos. Abaixo está a descrição de todos eles:
```Java
// Verificando se o processo foi concluído com sucesso
public boolean isSuccessful();

// Resgata link absoluto das imagens
public ArrayList<String> getImages();

// Resgata número de páginas (imagens) do álbum
public int getAlbumSize();

// Resgata nome do álbum
public String getAlbumName();

// Resgata o idioma do álbum
public String getAlbumLanguage();

// Verificando se o álbum é uma tradução
public boolean isTranslated();

// Resgatando nome do uploader
public String getUploader();

// Resgatando data do upload (yyyy/MM/dd - HH:mm)
public String getUploadDate();

// Resgatando tamanho do álbum (arquivos)
public String getFileSize();

// Resgata mensagem de erro (se houver, caso contrário retorna String vazia)
public String getError();
```

#### Requerimentos:
 - Java 8
 - org.jsoup

#### Exemplo de uso:
```Java
// albumURL é uma String, contendo o endereço do álbum desejado
EDownloaderInfo e = new EDownloaderInfo(albumURL);
e.getInfo();

if(e.isSuccessful()) {
    System.out.println("Nome do álbum: " + e.getAlbumName());
    System.out.println("Idioma: " + e.getAlbumLanguage());
    System.out.println("Tradução: " + e.isTranslated());
    System.out.println("Uploader: " + e.getUploader());
    System.out.println("Data: " + e.getUploadDate());
    System.out.println("Tamanho: " + e.getFileSize());
    System.out.println("Número de páginas: " + e.getAlbumSize());
    
    System.out.println("\nLink absoluto das imagens:");
    System.out.println("--------------------------");
    
    For(String img : e.getImages()) {
        System.out.println(img);
    }
} 
else {
    System.out.println("Erro! Não foi possível resgatar informações do álbum!");
    System.out.println("Erro: " + e.getError());
}
```

##### Ao utilizar uma URL correta para o álbum, o processo poderá ser iniciado sem grandes problemas. Caso utilize uma URL que não pertença ao e-hentai.org, isSuccessful() irá retornar falso e uma mensagem de erro será gerada para getError().
