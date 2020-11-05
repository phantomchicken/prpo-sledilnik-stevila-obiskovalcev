package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="zaposleni")
@NamedQueries(value =
        {
                @NamedQuery(name = "Zaposleni.getAll", query = "SELECT z FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getImePriimek", query = "SELECT z.ime, z.priimek FROM Zaposleni z"),
                @NamedQuery(name = "Zaposleni.getVhodUporabnika", query = "SELECT z.idVhoda FROM Zaposleni z")
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
    private Vhod idVhoda;

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getIme() { return ime; }

    public void setIme(String ime) { this.ime = ime; }

    public String getPriimek() { return priimek; }

    public void setPriimek(String priimek) { this.priimek = priimek; }
}
