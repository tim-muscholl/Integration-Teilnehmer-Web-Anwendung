package de.hdbw.hgy.primefaces.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DataScrollerView implements Serializable {
     
    private List<Teilnehmer> teilnehmers;
    
    private Teilnehmer selectedTeilnehmer;
    
    private Teilnehmer newTeilnehmer = new Teilnehmer();
    
    private Teilnehmer suchTeilnehmer = new Teilnehmer();
         
    
	@ManagedProperty("#{teilnehmerService}")
    private TeilnehmerService service;
     
    @PostConstruct
    public void init() {
    	teilnehmers = service.allTeilnehmers();
    }
 
    public List<Teilnehmer> getTeilnehmer() {
        return teilnehmers;
    }
 
    public void setService(TeilnehmerService service) {
        this.service = service;
    }
    
    public Teilnehmer getSelectedTeilnehmer() {
        return selectedTeilnehmer;
    }
 
    public void setSelectedTeilnehmer(Teilnehmer selectedTeilnehmer) {
        this.selectedTeilnehmer = selectedTeilnehmer;
    }
    
    public Teilnehmer getNewTeilnehmer() {
		return newTeilnehmer;
	}

	public void setNewTeilnehmer(Teilnehmer newTeilnehmer) {
		this.newTeilnehmer = newTeilnehmer;
	}

	public Teilnehmer getSuchTeilnehmer() {
		return suchTeilnehmer;
	}

	public void setSuchTeilnehmer(Teilnehmer suchTeilnehmer) {
		this.suchTeilnehmer = suchTeilnehmer;
	}
    
    /**
     * Save Action Methode for selected Teilnehmer
     */
    public void saveSelectedTeilnehmer() {
    	// Save selected Teilnehmer
        System.out.println("SAVE: Selected Teilnehmer ID=" + selectedTeilnehmer.id);
        
    	// Save selected Teilnehmer
        String result = service.aendernTeilnehmer(selectedTeilnehmer);
        System.out.println("SAVE: Aendern Teilnehmer: " + result);

    }
    
    /**
     * Cancel Action Methode for selected Teilnehmer
     */
    public void cancelSelectedTeilnehmer() {
        System.out.println("CANCEL: Selected Teilnehmer ID=" + selectedTeilnehmer.id);
    }
    
    /**
     * Save Action Methode for new Teilnehmer
     */
    public void saveNewTeilnehmer() {
    	// Save selected Teilnehmer
        int newId = service.saveNewTeilnehmer(newTeilnehmer);
        System.out.println("SAVE: New Teilnehmer ID; " + newId);

        // Template neu besetzen
        // newTeilnehmer = new Teilnehmer();
    }
    
    /**
     * Cancel Action Methode for new Teilnehmer
     */
    public void cancelNewTeilnehmer() {
        System.out.println("CANCEL: New Teilnehmer ");
    }
    
    /**
     * Suche Action Methode 
     */
    public void startSucheTeilnehmer() {
    	// Save selected Teilnehmer
        System.out.println("SUCHE: Starten");
        
        List<Teilnehmer> list = service.suchenTeilnehmers(suchTeilnehmer);
        
        System.out.println("SUCHE: hat ergeben: " + list.size());
        
        teilnehmers = list;
    }
    
    /**
     * Cancel Suche Action Methode 
     */
    public void cancelSucheTeilnehmer() {
        System.out.println("SUCHE: Cancel");
    }
    
}