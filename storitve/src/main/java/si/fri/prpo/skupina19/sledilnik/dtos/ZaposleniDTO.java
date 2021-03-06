package si.fri.prpo.skupina19.sledilnik.dtos;

import si.fri.prpo.skupina19.entitete.Vrata;

public class ZaposleniDTO {
    private Integer zaposleniId;
    private String vzdevek;
    private String ime;
    private String priimek;
    private Vrata vrata;

    public Integer getZaposleniId() {
        return zaposleniId;
    }

    public void setId(Integer zaposleniId) {
        this.zaposleniId = zaposleniId;
    }

    public String getVzdevek() {
        return vzdevek;
    }

    public void setVzdevek(String vzdevek) {
        this.vzdevek = vzdevek;
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

    @Override
    public String toString() {
        String vrataString = "";
        if (vrata!=null) if (vrata.getId()!=null) vrataString = vrata.getId().toString();
        return "ID: " + zaposleniId + "\n" + "vzdevek: "+ vzdevek  + "\n" + "ime: "+ ime  + "\n" + "priimek: " +priimek+ "\n" + "vrata: " + vrataString +"\n";
    }
}
