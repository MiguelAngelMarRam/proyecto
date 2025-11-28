package dam.pmdm.comparador_de_tiempo_proyecto_mamr_2025_26;

public class UsuarioHolder {
    private Usuario usuario;
    public Usuario getUsuarioLogueado() {return usuario;}
    public void setUsuarioLogueado(Usuario usuario) {this.usuario = usuario;}

    private static final UsuarioHolder holder = new UsuarioHolder();
    public static UsuarioHolder getInstance() {return holder;}
}
