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

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

import org.json.*;

// VERSÃO 1.0

// Classe responsável por resgatar o arquivo .json de uma API pública
// ==================================================================
public class GetJSON {
	// Propriedades privadas
	// =====================
	private JSONObject obj = null;
	private String apiURL = "";
	
	// Construtor da classe
	// ====================
	public GetJSON(String apiURL) {
		this.apiURL = apiURL;
	}
	
	// Métodos privados da classe
	// ==========================
	private void getJSON() {
		try {
			URL u = new URL(apiURL);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.connect();
			
			InputStream stream = (InputStream) conn.getContent();

			Scanner s = new Scanner(stream);
			s.useDelimiter("\\A");
			String res = s.hasNext() ? s.next() : "";
			s.close();

			obj = new JSONObject(res);
		}
		catch(MalformedURLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Métodos públicos da classe
	// ==========================
	public JSONObject get() {
		getJSON();
		return obj;
	}
}
