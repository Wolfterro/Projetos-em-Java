import java.time.*;

public class Contador extends Thread {
	// Propriedades privadas
	// =====================
	private int time = 0;
	private boolean running = true;

	// Métodos públicos
	// ================

	// Iniciando Thread
	// ================
	@Override
	public void run() {
		while(running) {
			time += 1;
			try {
				Thread.sleep(1000);
			}
			catch(InterruptedException e) {
				e.printStackTrace();
				running = false;
			}
		}
	}

	// Resgatando o tempo em segundos
	// ==============================
	public int getTime() {
		return time;
	}

	// Resgatando o tempo formatado
	// ============================
	public String getTimeFormatted() {
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		return formatter.format(new Date(time * 1000));
		*/
		LocalTime t = LocalTime.ofSecondOfDay(time);
		return t.toString();
	}

	// Parando Thread
	// ==============
	public void stopCounter() {
		running = false;
	}
}
