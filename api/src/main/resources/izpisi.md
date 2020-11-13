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
 
 /*writer.append("<hr>");
writer.append("<h1>Testiranje CRUD operacij</h1>"); 
         writer.append("<h3>Create prostor</h3><p>");
         Prostor prostor = new Prostor();
         prostor.setId(3);
         prostor.setImeProstora("Bazen");
         prostor.setKvadratovPoOsebi(10);
         prostor.setKvadratura(1000);
         prostor.setStVrat(7);
         prostor.setTrenutnoOseb(100);
         prostorZrno.createProstor(prostor);
         izpis(writer,'p');*/
    
/*writer.append("<h3>Create zaposleni</h3><p>");
        Zaposleni zaposleni = new Zaposleni();
        zaposleni.setIme("Patrick");
        zaposleni.setPriimek("Ewing");
        zaposleni.setId(4);
        zaposleniZrno.createZaposleni(zaposleni);
        izpis(writer,'z');*/
        
 /*writer.append("<h3>Update prostor</h3><p>");
         prostor.setImeProstora("BazenNEW");
         prostorZrno.updateProstor(3,prostor);
         izpis(writer,'p');
 
         writer.append("<h3>Update zaposleni</h3><p>");
         zaposleni.setIme("Patrick Aloysius");
         zaposleniZrno.updateZaposleni(4,zaposleni);
         izpis(writer,'z');
 
         writer.append("<h3>Delete prostor</h3><p>");
         prostorZrno.deleteProstor(3);
         izpis(writer,'p');
 
         writer.append("<h3>Delete zaposleni</h3><p>");
         zaposleniZrno.deleteZaposleni(4);
         izpis(writer,'z');*/         
         
 ### Poslovne operacije
 
  //POPRAVITI?
         //writer.append("<h1>Test</h1>");
         //zaposleniZrno.getDelovnoMesto().stream().forEach( z -> writer.append("<li>"+ z.toString() + "</li>"));
         
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