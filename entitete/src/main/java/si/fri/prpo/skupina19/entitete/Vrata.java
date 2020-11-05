package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;


@Entity
@Table(name="vrata")
@NamedQueries(value =
        {
                @NamedQuery(name = "vrata.getAll", query = "SELECT v FROM Vrata v"),
                @NamedQuery(name = "vrata.getStVhodov", query = "SELECT v.stVhodov FROM Vrata v WHERE v.id = :id"),
                @NamedQuery(name = "vrata.getStIzhodov", query = "SELECT v.stIzhodov FROM Vrata v WHERE v.id = :id")
        })

public class Vrata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="st_vhodov")
    private String stVhodov;

    @Column(name="st_izhodov")
    private String stIzhodov;

    /*@ManyToOne
    private Prostor idProstora;*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStVhodov() {
        return stVhodov;
    }

    public void setStVhodov(String stVhodov) {
        this.stVhodov = stVhodov;
    }

    public String getStIzhodov() {
        return stIzhodov;
    }

    public void setStIzhodov(String stIzhodov) {
        this.stIzhodov = stIzhodov;
    }
}
