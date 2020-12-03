package si.fri.prpo.skupina19.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;


@Entity
@Table(name="zaposleni")
@NamedQueries(value =
        {
                @NamedQuery(name = "Zaposleni.getAll", query = "SELECT z FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getImeInPriimek", query = "SELECT z.ime, z.priimek FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getVrataUporabnika", query = "SELECT z.vrata FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getDelovnoMesto", query = "SELECT p.id FROM Zaposleni z, Vrata v, Prostor p WHERE p.id = v.prostor.id AND v.id = z.vrata.id"),
                @NamedQuery(name = "Zaposleni.getZaposleniVzdevek", query = "SELECT z FROM Zaposleni z WHERE z.vzdevek = :vzdevek")
        })
public class Zaposleni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="vzdevek")
    private String vzdevek;

    @Column(name="ime")
    private String ime;

    @Column(name="priimek")
    private String priimek;

    //@OneToOne(mappedBy = "zaposleni", cascade = CascadeType.ALL)
    //private Vrata vrata;
    //@JsonbTransient
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Vrata vrata;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() { return priimek; }

    public String getVzdevek() {
        return vzdevek;
    }

    public void setVzdevek(String vzdevek) {
        this.vzdevek = vzdevek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Vrata getVrata() { return vrata; }

    public void setVrata(Vrata vrata) {
        this.vrata = vrata;
    }

    @Override
    public String toString() {
        String vrataString = "null";
        if (vrata != null && vrata.getId()!= null){
            vrataString=vrata.getId().toString();
        }
        return "ID: " + id + "\n" + "ime: " + this.ime + "\n"  + "priimek: " + this.priimek +"\n" + "vzdevek: " + this.vzdevek +"\n" + "vrata: " + vrataString + "\n";
    }
}
