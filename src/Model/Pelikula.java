package Model;
// Generated 10-dic-2018 21:29:26 by Hibernate Tools 4.3.1

/**
 * Pelikula generated by hbm2java
 */
public class Pelikula implements java.io.Serializable {

    private String izena;
    private String direktorea;
    private Integer urtea;
    private String generoa;
    private String ikusita;

    public Pelikula() {
    }

    public Pelikula(String izena) {
        this.izena = izena;
    }

    public Pelikula(String izena, String direktorea, Integer urtea, String generoa, String ikusita) {
        this.izena = izena;
        this.direktorea = direktorea;
        this.urtea = urtea;
        this.generoa = generoa;
        this.ikusita = ikusita;
    }

    public String getIzena() {
        return this.izena;
    }

    public void setIzena(String izena) {
        this.izena = izena;
    }

    public String getDirektorea() {
        return this.direktorea;
    }

    public void setDirektorea(String direktorea) {
        this.direktorea = direktorea;
    }

    public Integer getUrtea() {
        return this.urtea;
    }

    public void setUrtea(Integer urtea) {
        this.urtea = urtea;
    }

    public String getGeneroa() {
        return this.generoa;
    }

    public void setGeneroa(String generoa) {
        this.generoa = generoa;
    }

    public String getIkusita() {
        return this.ikusita;
    }

    public void setIkusita(String ikusita) {
        this.ikusita = ikusita;
    }

}
