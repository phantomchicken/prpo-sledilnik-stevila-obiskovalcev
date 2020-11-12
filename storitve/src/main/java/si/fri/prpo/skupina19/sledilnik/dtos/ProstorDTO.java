package si.fri.prpo.skupina19.sledilnik.dtos;

public class ProstorDTO {
    private Integer prostorId;
    private String imeProstora;
    private Integer kvadratovPoOsebi;
    private Integer kvadratura;
    private Integer stVrat;
    private Integer trenutnoOseb;

    public Integer getProstorId() {
        return prostorId;
    }

    public void setProstorId(Integer prostorId) {
        this.prostorId = prostorId;
    }

    public String getImeProstora() {
        return imeProstora;
    }

    public void setImeProstora(String imeProstora) {
        this.imeProstora = imeProstora;
    }

    public Integer getKvadratovPoOsebi() {
        return kvadratovPoOsebi;
    }

    public void setKvadratovPoOsebi(Integer kvadratovPoOsebi) {
        this.kvadratovPoOsebi = kvadratovPoOsebi;
    }

    public Integer getKvadratura() {
        return kvadratura;
    }

    public void setKvadratura(Integer kvadratura) {
        this.kvadratura = kvadratura;
    }

    public Integer getStVrat() {
        return stVrat;
    }

    public void setStVrat(Integer stVrat) {
        this.stVrat = stVrat;
    }

    public Integer getTrenutnoOseb() {
        return trenutnoOseb;
    }

    public void setTrenutnoOseb(Integer trenutnoOseb) {
        this.trenutnoOseb = trenutnoOseb;
    }
}
