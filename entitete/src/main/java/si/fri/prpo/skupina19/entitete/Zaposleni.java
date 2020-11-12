package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="zaposleni")
@NamedQueries(value =
        {
                @NamedQuery(name = "Zaposleni.getAll", query = "SELECT z FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getImeInPriimek", query = "SELECT z.ime, z.priimek FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getVrataUporabnika", query = "SELECT z.vrata FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getDelovnoMesto", query = "SELECT p.id FROM Zaposleni z, Vrata v, Prostor p WHERE p.id = v.prostor.id AND v.id = z.vrata.id")
        })
public class Zaposleni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ime")
    private String ime;

    @Column(name="priimek")
    private String priimek;

    @OneToOne(mappedBy = "zaposleni")
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

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public Vrata getVrata() { return vrata; }

    public void setVrata(Vrata vrata) {
        this.vrata = vrata;
    }

    @Override
    public String toString() {
        return "ime: " + this.ime + "\n"  + "priimek: " + this.priimek +"\n" + "vrata: " + vrata;
    }
}
