package si.fri.prpo.skupina19.sledilnik.dtos;

import si.fri.prpo.skupina19.entitete.Vrata;

public class ZaposleniDTO {
    private Integer zaposleniId;
    private String ime;
    private String priimek;
    private Vrata vrata;

    public Integer getZaposleniId() {
        return zaposleniId;
    }

    public void setId(Integer zaposleniId) {
        this.zaposleniId = zaposleniId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Vrata getVrata() {
        return vrata;
    }

    public void setVrata(Vrata vrata) {
        this.vrata = vrata;
    }
}
