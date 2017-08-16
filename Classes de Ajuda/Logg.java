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

import java.io.*;
import java.text.*;
import java.util.*;

// VERSÃO 1.0

public class Logg {
	// Propriedades privadas
	// =====================
	private String filename = "MyLogg.txt";
	private String dateFormat = "HH:mm:ss | dd/MM/yyyy";

	private PrintWriter pw = null;

	// Construtores da classe
	// ======================
	public Logg() { }

	public Logg(String filename) {
		this.filename = filename;
	}

	// ================
	// Métodos privados
	// ================

	// Iniciando arquivo de log
	// ========================
	private void initLog() {
		try {
			pw = new PrintWriter(new FileOutputStream(new File(filename), true));
		}
		catch(IOException e) {
			e.printStackTrace();
			pw = null;
		}
	}

	// ================
	// Métodos públicos
	// ================

	// Escrevendo no arquivo de log sem data
	// =====================================
	public void writeLogg(String message) {
		initLog();

		if(pw != null) {
			pw.println(message);
			pw.close();
		}
		else {
			String err = String.format(
				"ERROR! Logg: PrintWriter object is Null at message: %s",
				message);
			System.err.println(err);
		}
	}

	public void writeLogg() {
		this.writeLogg("");
	}

	// Escrevendo no arquivo de log com data
	// =====================================
	public void writeDatedLogg(String message) {
		initLog();

		if(pw != null) {
			String date = new SimpleDateFormat(
				dateFormat).format(new Date());

			pw.println(String.format("[%s] %s", date, message));
			pw.close();
		}
		else {
			String err = String.format(
				"ERROR! Logg: PrintWriter object is Null at message: %s",
				message);
			System.err.println(err);
		}
	}

	public void writeDatedLogg() {
		this.writeDatedLogg("");
	}

	// Configurando o formato de data
	// ==============================
	public void setDateFormat(String dateFormat) {
		// Testando formato de data inserido pelo usuário
		// ----------------------------------------------
		try {
			SimpleDateFormat s = new SimpleDateFormat(dateFormat);
			this.dateFormat = dateFormat;
		}
		catch(Exception e) {
			// Ignorar valor no caso de falha no teste
			// ---------------------------------------
			return;
		}
	}
}
