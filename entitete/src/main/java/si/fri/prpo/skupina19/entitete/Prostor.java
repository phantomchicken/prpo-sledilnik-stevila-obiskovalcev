package si.fri.prpo.skupina19.entitete;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="prostor")
@NamedQueries(value =
        {
                @NamedQuery(name = "Prostor.getAll", query = "SELECT p FROM Prostor p"),
                @NamedQuery(name = "Prostor.getIme", query = "SELECT p.imeProstora FROM Prostor p"),
                @NamedQuery(name = "Prostor.getStVrat", query = "SELECT p.stVrat FROM Prostor p"),
                @NamedQuery(name = "Prostor.getTrOseb", query = "SELECT p.trenutnoOseb FROM Prostor p")
        })

public class Prostor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ime_prostora")
    private String imeProstora;

    @Column(name="st_vrat")
    private Integer stVrat;

    @Column(name="trenutno_oseb")
    private Integer trenutnoOseb;

    @Column(name="omejitev_oseb")
    private Integer omejitevOseb;

    @OneToMany(mappedBy = "prostor")
    private List<Vrata> seznamVrat;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImeProstora() {
        return imeProstora;
    }

    public void setImeProstora(String imeProstora) {
        this.imeProstora = imeProstora;
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

    public Integer getOmejitevOseb() {
        return omejitevOseb;
    }

    public void setOmejitevOseb(Integer omejitevOseb) {
        this.omejitevOseb = omejitevOseb;
    }

    public List<Vrata> getSeznamVrat() {
        return seznamVrat;
    }

    public void setSeznamVrat(List<Vrata> seznamVrat) {
        this.seznamVrat = seznamVrat;
    }

    @Override
    public String toString() {
        return this.imeProstora;
    }
}