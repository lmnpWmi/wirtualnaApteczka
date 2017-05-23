package lmnp.wirtualnaapteczka;

/**
 * klasa definiujaca lek
 */

class Medicine {

    //nazwa leku
    private String nameMedicine;

    //rodzaj leku
    private String kindOfMedicine;

    //konstruktor nazwa i rodzaj leku

    public Medicine(String nameMedicine, String kindOfMedicine) {
        this.nameMedicine = nameMedicine;
        this.kindOfMedicine = kindOfMedicine;
    }

    //getter dla nazwy leku
    public String getNameMedicine() {
        return nameMedicine;
    }

    //getter dla rodzaju leku
    public String getKindOfMedicine() {
        return kindOfMedicine;
    }
}
