package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="vrata")
@NamedQueries(value =
        {
                @NamedQuery(name = "Vrata.getAll", query = "SELECT v FROM Vrata v"),
                @NamedQuery(name = "Vrata.getStVstopov", query = "SELECT v.stVstopov FROM Vrata v WHERE v.id = :id"),
                @NamedQuery(name = "Vrata.getStIzstopov", query = "SELECT v.stIzstopov FROM Vrata v WHERE v.id = :id"),
                @NamedQuery(name = "Vrata.getSt", query = "SELECT v.stVstopov, v.stIzstopov FROM Vrata v"),
        })

public class Vrata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="st_vstopov")
    private Integer stVstopov;

    @Column(name="st_izstopov")
    private Integer stIzstopov;

    @OneToOne
    @JoinColumn(name = "zaposleni_id")
    private Zaposleni zaposleni;

    @ManyToOne
    @JoinColumn(name = "prostor_id")
    private Prostor prostor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setStIzstopov(Integer stIzstopov) { this.stIzstopov = stIzstopov; }

    public Zaposleni getZaposleni() { return zaposleni; }

    public void setZaposleni(Zaposleni zaposleni) { this.zaposleni = zaposleni; }

    public Prostor getProstor() { return prostor; }

    public void setProstor(Prostor prostor) { this.prostor = prostor; }

    @Override
    public String toString() {
        String zaposleniString ="";
        String prostorString = "";
        if (zaposleni.getId()!=null) zaposleniString = zaposleni.getId().toString();
        if (prostor.getId()!=null) prostorString = prostor.getId().toString();
        return "ID: " + id + "\n" + "prostorID: "+ prostorString  + "\n" + "zaposleniID: "+ zaposleniString  + "\n" + "stVstopov: " +stVstopov+ "\n" + "stIzstopov" + stIzstopov +"\n";
        // "ID: " + id +"\n"  +
        //"prostorID: " + prostor.toString() + "\n"
    }

}
