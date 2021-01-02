package si.fri.prpo.skupina19.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name="prostor")
@NamedQueries(value =
        {
                @NamedQuery(name = "Prostor.getAll", query = "SELECT p FROM Prostor p"),
                @NamedQuery(name = "Prostor.getIme", query = "SELECT p.imeProstora FROM Prostor p"),
                @NamedQuery(name = "Prostor.getStVrat", query = "SELECT p.stVrat FROM Prostor p"),
                @NamedQuery(name = "Prostor.getTrOseb", query = "SELECT p.trenutnoOseb FROM Prostor p"),
                @NamedQuery(name = "Prostor.getProstorZImenom", query = "SELECT p FROM Prostor p WHERE p.imeProstora = :ime")
        })

public class Prostor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="kvadratura")
    private Integer kvadratura;

    @Column(name="ime_prostora")
    private String imeProstora;

    @Column(name="st_vrat")
    private Integer stVrat;

    @Column(name="trenutno_oseb")
    private Integer trenutnoOseb;

    @Column(name="kvadratov_po_osebi")
    private Integer kvadratovPoOsebi;

    //@JsonbTransient
    @OneToMany(mappedBy = "prostor", cascade = CascadeType.ALL)
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

    public void setTrenutnoOseb(Integer oseb) {
        this.trenutnoOseb = oseb;
    }

    public Integer getKvadratovPoOsebi() { return kvadratovPoOsebi; }

    public void setKvadratovPoOsebi(Integer kvadratovPoOsebi) {
        this.kvadratovPoOsebi = kvadratovPoOsebi;
    }

    public List<Vrata> getSeznamVrat() {
        return seznamVrat;
    }

    public void setSeznamVrat(List<Vrata> seznamVrat) {
        this.seznamVrat = seznamVrat;
    }

    public Integer getKvadratura() { return kvadratura; }

    public void setKvadratura(Integer kvadratura) { this.kvadratura = kvadratura; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vrata vrata: seznamVrat){
            sb.append(vrata.getId() + ", ");
        }
        String vrataString = "";

        // če vrata niso nastavljena
        if (sb.length()>2){
            vrataString = sb.substring(0,sb.length()-2);
        } else {
            vrataString = sb.toString();
        }
        return "ID: " + id + "\n" + "ime " + imeProstora + "\n" + "stVrat " + stVrat + "\n" + "kvadratura " + kvadratura +"\n" + "kvadratovPoOsebi " + kvadratovPoOsebi + "\n" + "trenutnoOseb " + trenutnoOseb + "\n"  +"ID-ji vrat " + vrataString + "\n";
        //return String.format("ime %s%n stVrat %d%n kvadratura %d%n kvadratovPoOsebi %d%n trenutnoOseb %d%n",imeProstora, stVrat, kvadratura, kvadratovPoOsebi, trenutnoOseb);
    }
}