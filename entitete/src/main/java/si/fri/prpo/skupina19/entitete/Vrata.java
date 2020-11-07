package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="vrata")
@NamedQueries(value =
        {
                @NamedQuery(name = "Vrata.getAll", query = "SELECT v FROM Vrata v"),
                @NamedQuery(name = "Vrata.getStVhodov", query = "SELECT v.stVhodov FROM Vrata v WHERE v.id = :id"),
                @NamedQuery(name = "Vrata.getStIzhodov", query = "SELECT v.stIzhodov FROM Vrata v WHERE v.id = :id")
        })

public class Vrata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="st_vhodov")
    private Integer stVhodov;

    @Column(name="st_izhodov")
    private Integer stIzhodov;

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

    public Integer getStVhodov() {
        return stVhodov;
    }

    public void setStVhodov(Integer stVhodov) {
        this.stVhodov = stVhodov;
    }

    public Integer getStIzhodov() {
        return stIzhodov;
    }

    public void setStIzhodov(Integer stIzhodov) {
        this.stIzhodov = stIzhodov;
    }

}
