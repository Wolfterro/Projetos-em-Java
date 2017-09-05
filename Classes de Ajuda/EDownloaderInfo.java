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

// VERSÃO 1.0

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
	private List<String> imageLinks = new ArrayList<String>();
	
	private String albumName = "";
	private String error = "";
	
	private int pages = 0;
	
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
	// =====================================
	private boolean checkURLDomain() {
		try {
			URL u = new URL(albumURL);
			if(u.getAuthority().equals("e-hentai.org")) {
				return true;
			}
			else {
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
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
	public List<String> getImages() {
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
	
	// Resgatando mensagem de erro
	// ---------------------------
	public String getError() {
		return error;
	}
}
