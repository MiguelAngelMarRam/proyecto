package dam.pmdm.comparador_de_tiempo_proyecto_mamr_2025_26;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Historial implements Serializable {
    //Atributo
    private List<Comparacion> historial;

    public Historial() {
        historial = new ArrayList<Comparacion>();
    }
    public Historial(List<Comparacion> historial) {
        this.historial = historial;
    }

    public List<Comparacion> getHistorial() {
        return historial;
    }

    public void setHistorial(List<Comparacion> historial) {
        this.historial = historial;
    }

    //ToString
    @Override
    public String toString() {
        return "Historial TBD";
    }
}
