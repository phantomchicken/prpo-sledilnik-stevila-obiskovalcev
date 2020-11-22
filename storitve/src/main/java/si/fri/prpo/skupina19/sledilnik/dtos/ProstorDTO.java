package si.fri.prpo.skupina19.sledilnik.dtos;

import si.fri.prpo.skupina19.entitete.Vrata;

import java.util.List;

public class ProstorDTO {
    private Integer prostorId;
    private String imeProstora;
    private Integer kvadratovPoOsebi;
    private Integer kvadratura;
    private Integer stVrat;
    private Integer trenutnoOseb;
    private List<Vrata> seznamVrat;

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

    public List<Vrata> getSeznamVrat() { return seznamVrat; }

    public void setSeznamVrat(List<Vrata> seznamVrat) { this.seznamVrat = seznamVrat; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vrata vrata: seznamVrat)
            sb.append(vrata.getId() + ", ");

        String vrataString = "";

        // če vrata niso nastavljena
        if (sb.length()>2) vrataString = sb.substring(0,sb.length()-2);
        else vrataString = sb.toString();
        return "ID: " + prostorId + "\n" + "ime " + imeProstora + "\n" + "stVrat " + stVrat + "\n" + "kvadratura " + kvadratura +"\n" + "kvadratovPoOsebi " + kvadratovPoOsebi + "\n" + "trenutnoOseb " + trenutnoOseb + "\n"  +"ID-ji vrat " + vrataString + "\n";
    }
}
