package si.fri.prpo.skupina19.sledilnik.dtos;

import si.fri.prpo.skupina19.entitete.Prostor;
import si.fri.prpo.skupina19.entitete.Zaposleni;

public class VrataDTO {
    private Integer vrataId;
    private Integer stVstopov;
    private Integer stIzstopov;
    private Prostor prostor;
    private Zaposleni zaposleni;

    public Integer getVrataId() {
        return vrataId;
    }

    public void setVrataId(Integer vrataId) {
        this.vrataId = vrataId;
    }

    public Integer getStVstopov() {
        return stVstopov;
    }

    public void setStVstopov(Integer stVstopov) {
        this.stVstopov = stVstopov;
    }

    public Integer getStIzstopov() {
        return stIzstopov;
    }

    public void setStIzstopov(Integer stIzstopov) {
        this.stIzstopov = stIzstopov;
    }

    public Prostor getProstor() {
        return prostor;
    }

    public void setProstor(Prostor prostor) {
        this.prostor = prostor;
    }

    public Zaposleni getZaposleni() {
        return zaposleni;
    }

    public void setZaposleni(Zaposleni zaposleni) {
        this.zaposleni = zaposleni;
    }

    @Override
    public String toString() {
        String zaposleniString ="";
        String prostorString = "";
        if (zaposleni!=null) if (zaposleni.getId()!=null) zaposleniString = zaposleni.getId().toString();
        if (prostor!=null) if (prostor.getId()!=null) prostorString = prostor.getId().toString();
        return "ID: " + vrataId + "\n" + "prostorID: "+ prostorString  + "\n" + "zaposleniID: "+ zaposleniString  + "\n" + "stVstopov: " +stVstopov+ "\n" + "stIzstopov: " + stIzstopov +"\n";
    }
}
