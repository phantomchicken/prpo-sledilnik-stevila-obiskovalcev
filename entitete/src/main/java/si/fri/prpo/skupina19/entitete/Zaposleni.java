package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="zaposleni")
@NamedQueries(value =
        {
                @NamedQuery(name = "zaposleni.getAll", query = "SELECT z FROM Zaposleni z"),
                @NamedQuery(name = "zaposleni.getImePriimek", query = "SELECT z.ime, z.priimek FROM Zaposleni z"),
                @NamedQuery(name = "zaposleni.getVrataUporabnika", query = "SELECT z.id_vrata FROM Zaposleni z")
        })
public class Zaposleni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ime")
    private String ime;

    @Column(name="priimek")
    private String priimek;

    @OneToOne
    private Vrata id_vrata;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getIme() { return ime; }

    public void setIme(String ime) { this.ime = ime; }

    public String getPriimek() { return priimek; }

    public void setPriimek(String priimek) { this.priimek = priimek; }

    public Vrata getId_vrata() { return id_vrata; }

    public void setId_vrata(Vrata id_vrata) { this.id_vrata = id_vrata; }

    @Override
    public String toString() {
        return ime + " " + priimek + " trenutno na vratih " + id_vrata;
    }
}
