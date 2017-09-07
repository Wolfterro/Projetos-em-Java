/*
The MIT License (MIT)

Copyright (c) 2017 Wolfgang Almeida

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.github.wolfterro;

import java.io.*;
import java.util.*;
import java.net.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

// VERSÃO 1.1

public class EDownloaderInfo {
	// Propriedades privadas
	// =====================
	
	// Propriedades recebidas
	// ----------------------
	private String albumURL = "";

	// Propriedades enviadas ou estocadas
	// ----------------------------------
	private Document doc = null;
	
	private Elements links = null;
	
	private List<String> imagePages = new ArrayList<String>();
	private List<String> albumPages = new ArrayList<String>();
	
	// As propriedades abaixo poderão retornar
	// ---------------------------------------
	private ArrayList<String> imageLinks = new ArrayList<String>();
	
	private String albumName = "";
	private String albumLanguage = "";
	private String uploadDate = "";
	private String uploader = "";
	private String fileSize = "";
	private String error = "";
	
	private int pages = 0;
	
	private boolean isTranslated = false;
	private boolean isOk = false;
	
	// Construtor da Classe
	// ====================
	public EDownloaderInfo(String albumURL) {
		this.albumURL = albumURL;
	}
	
	// ================
	// Métodos privados
	// ================
	
	// Recuperando informações sobre o álbum
	// -------------------------------------
	private boolean pGetInfo() {
		if(!checkURLDomain()) {
			error = "INVALID_URL_ERROR";
			return false;
		}
		
		try {
			doc = Jsoup.connect(albumURL).cookie("nw", "1").get();
			albumName = String.format("%s", doc.title()).replace(" - E-Hentai Galleries", "");
			
			pGetAlbumLanguage();
			pGetUploader();
			pGetUploadDate();
			pGetFileSize();
			pGetAlbumPages();
			pGetImagePages();
			pGetImageLinks();
			
			pages = imageLinks.size();
			
			return true;
		}
		catch(IOException e) {
			e.printStackTrace();
			error = "IO_EXCEPTION_AT_pGetInfo()";
			return false;
		}
	}
	
	// Verificando se a URL pertence ao site
	// -------------------------------------
	private boolean checkURLDomain() {
		try {
			URL u = new URL(albumURL);
			if(u.getAuthority().equals("e-hentai.org")) {
				// Caso o usuário insira além da primeira página
				// ---------------------------------------------
				albumURL = albumURL.replaceAll("\\?p=.*", "");
				return true;
			}
			else {
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// Resgatando idioma do álbum
	// --------------------------
	private void pGetAlbumLanguage() {
		links = doc.select("div[id=gdd]");
		
		for(Element e : links) {
			Elements table = e.getElementsByTag("table");
			
			for(Element t : table) {
				Elements row = t.getElementsByTag("tr");
				
				for(Element l : row) {
					Elements lang = l.getElementsByClass("gdt2");
					Elements langDescr = l.getElementsByClass("gdt1");
					
					int i = 0;
					for(Element ll : langDescr) {
						if(ll.text().toString().contains("Language")) {
							if(lang.get(i).text().toString().contains("TR")) {
								isTranslated = true;
							}
							
							albumLanguage = lang.get(i)
									.text()
									.toString()
									.replace(" &nbsp;", "")
									.replace("TR", "");
							return;
						}
						
						i += 1;
					}
				}
			}
		}
	}
	
	// Resgatando nome do uploader
	// ---------------------------
	private void pGetUploader() {
		links = doc.select("div[id=gdn]");
		
		for(Element e : links) {
			Elements uplName = e.getElementsByTag("a");
			uploader = uplName.text().toString();
		}
	}
	
	// Resgatando data do upload
	// -------------------------
	private void pGetUploadDate() {
		links = doc.select("div[id=gdd]");
		
		for(Element e : links) {
			Elements table = e.getElementsByTag("table");
			
			for(Element t : table) {
				Elements row = t.getElementsByTag("tr");
				
				for(Element d : row) {
					Elements date = d.getElementsByClass("gdt2");
					
					for(Element dd : date) {
						uploadDate = dd.text().toString()
								.replace("-", "/")
								.replace(" ", " - ");
						return;
					}
				}
			}
		}
	}
	
	// Resgatando tamanho do álbum
	// ---------------------------
	private void pGetFileSize() {
		links = doc.select("div[id=gdd]");
		String bytes[] = {"B", "KB", "MB", "GB", "TB"};
		
		for(Element e : links) {
			Elements table = e.getElementsByTag("table");
			
			for(Element t : table) {
				Elements row = t.getElementsByTag("tr");
				
				for(Element f : row) {
					Elements fs = f.getElementsByClass("gdt2");
					
					for(String b : bytes) {
						if(fs.text().toString().endsWith(b)) {
							fileSize = fs.text().toString();
						}
					}
				}
			}
		}
	}
	
	// Resgatando páginas do álbum, da forma como é mostrado no site
	// -------------------------------------------------------------
	private void pGetAlbumPages() {
		links = doc.select("a[href]");
		
		for(Element e : links) {
			if(e.absUrl("href").contains("?p=")) {
				if(!albumPages.contains(e.absUrl("href"))) {
					albumPages.add(e.absUrl("href"));
				}
			}
		}
	}
	
	// Resgatando link das imagens, como são mostradas no site
	// -------------------------------------------------------
	private void pGetImagePages() {
		for(Element e : links) {
			if(e.absUrl("href").startsWith("https://e-hentai.org/s/")) {
				imagePages.add(e.absUrl("href"));
			}
		}
		
		if(albumPages.size() >= 1) {
			for(int i = 0; i < albumPages.size(); i++) {
				try {
					doc = Jsoup.connect(albumPages.get(i)).cookie("nw", "1").get();
					links = doc.select("a[href]");
					
					for(Element e : links) {
						if(e.absUrl("href").startsWith("https://e-hentai.org/s/")) {
							imagePages.add(e.absUrl("href"));
						}
					}
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// Resgatando link absoluto das imagens através das páginas
	// --------------------------------------------------------
	private void pGetImageLinks() { 
		for(int i = 0; i < imagePages.size(); i++) {
			try {
				doc = Jsoup.connect(imagePages.get(i)).cookie("nw", "1").get();
				links = doc.select("img[id=img]");
				
				for(Element img : links) {
					imageLinks.add(img.absUrl("src"));
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ================
	// Métodos públicos
	// ================
	
	// Iniciando processo de resgate de valores do álbum
	// -------------------------------------------------
	public void getInfo() {
		isOk = pGetInfo();
	}
	
	// Verificando se o processo foi concluído com sucesso
	// ---------------------------------------------------
	public boolean isSuccessful() {
		return isOk;
	}
	
	// Resgatando link absoluto das imagens
	// ------------------------------------
	public ArrayList<String> getImages() {
		return imageLinks;
	}
	
	// Resgatando número de páginas (imagens) do álbum
	// -----------------------------------------------
	public int getAlbumSize() {
		return pages;
	}
	
	// Resgatando nome do álbum
	// ------------------------
	public String getAlbumName() {
		return albumName;
	}
	
	// Resgatando idioma do álbum
	// --------------------------
	public String getAlbumLanguage() {
		return albumLanguage;
	}
	
	// Verificando se o álbum é uma tradução
	// -------------------------------------
	public boolean isTranslated() {
		return isTranslated;
	}
	
	// Resgatando nome do uploader
	// ---------------------------
	public String getUploader() {
		return uploader;
	}
	
	// Resgatando data do upload
	// -------------------------
	public String getUploadDate() {
		return uploadDate;
	}
	
	// Resgatando tamanho do álbum
	// ---------------------------
	public String getFileSize() {
		return fileSize;
	}
	
	// Resgatando mensagem de erro
	// ---------------------------
	public String getError() {
		return error;
	}
}
