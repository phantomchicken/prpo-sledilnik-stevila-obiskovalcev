## CriteriaAPI
/*izpis vseh uporabnikov s CriteriaAPI
        writer.append("<h3>Zaposleni s CriteriaAPI</h3>");
        writer.append("<p>Zaposleni so:");
        izpisCriteria(writer,'z');*/

/*izpis stanja vrat s CriteriaAPI
        writer.append("<h3>Vrata s CriteriaAPI</h3>");
        writer.append("<p>Stanja vrat  so:");
        izpisCriteria(writer,'v');*/

/*izpis vseh prostorov s CriteriaAPI
        writer.append("<h3>Prostori s CriteriaAPI</h3>");
        writer.append("<p>Prostori so:");
        izpisCriteria(writer,'p');*/
        
       
 ## CRUD:
 
 writer.append("<h1>Testiranje CRUD operacij</h1>");
         writer.append("<h3>Create prostor</h3><p>");
         Prostor prostor = new Prostor();
         prostor.setImeProstora("Bazen");
         prostor.setKvadratovPoOsebi(10);
         prostor.setKvadratura(1000);
         prostor.setStVrat(7);
         prostor.setTrenutnoOseb(100);
         prostorZrno.createProstor(prostor);
         izpis(writer,'p');
 
 
 writer.append("<h3>Create zaposleni</h3><p>");
         Zaposleni zaposleni = new Zaposleni();
         zaposleni.setIme("Patrick");
         zaposleni.setPriimek("Ewing");
         zaposleniZrno.createZaposleni(zaposleni);
         izpis(writer,'z');
 
         writer.append("<h3>Update prostor</h3><p>");
         prostor.setImeProstora("BazenNEW");
         prostorZrno.updateProstor(4,prostor);
         izpis(writer,'p');
 
         writer.append("<h3>Update zaposleni</h3><p>");
         zaposleni.setIme("Patrick Aloysius");
         zaposleniZrno.updateZaposleni(5,zaposleni);
         izpis(writer,'z');
 
         writer.append("<h3>Delete prostor</h3><p>");
         prostorZrno.deleteProstor(4);
         izpis(writer,'p');
 
         writer.append("<h3>Delete zaposleni</h3><p>");
         zaposleniZrno.deleteZaposleni(5);
         izpis(writer,'z');        
         
         
        Zaposleni z2 = zaposleniZrno.getZaposleni(1);
        if (z2!=null) z2.setVzdevek("yeaa boi");
        zaposleniZrno.updateZaposleni(1,z2);
        izpis(writer,'z');
        zaposleniZrno.deleteZaposleni(1);
        izpis(writer,'z');
        vrataZrno.deleteVrata(1);
        izpis(writer,'v');
        prostorZrno.deleteProstor(1);
        izpis(writer,'p');
         
 ### Poslovne operacije
 
  //POPRAVITI?
         //writer.append("<h1>Test</h1>");
         //zaposleniZrno.getDelovnoMesto().stream().forEach( z -> writer.append("<li>"+ z.toString() + "</li>"));
         
                Prostor lekarna = prostorZrno.getProstor(2);
                 ProstorDTO prostorDTO2 = new ProstorDTO();
                 prostorDTO2.setImeProstora(lekarna.getImeProstora());
                 prostorDTO2.setKvadratovPoOsebi(lekarna.getKvadratPoOsebi());
                 prostorDTO2.setKvadratura(lekarna.getKvadratura());
                 prostorDTO2.setStVrat(lekarna.getStVrat());
                 prostorDTO2.setTrenutnoOseb(lekarna.getTrenutnoOseb());
                 prostorDTO2.setProstorId(lekarna.getId());
         
         
                 writer.append(upravljanjeProstorovZrno.getOmejitev(prostorDTO2).toString());
         
 ### Scopes
 
        VrataDTO vrataDTO2 = new VrataDTO();
         vrataDTO2.setVrataId(5);
         vrataDTO2.setStVstopov(23);
         vrataDTO2.setStIzstopov(24);
         vrataDTO2.setProstor(p);
         vrataDTO2.setZaposleni(z);
         Vrata v2 = upravljanjeVrataZrno.createVrata(vrataDTO2);
 
         upravljanjeVrataZrno.deleteVrata(vrataDTO);
         VrataZrno --> RequestScoped
         refresh
  