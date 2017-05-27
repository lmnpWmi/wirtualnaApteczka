package lmnp.wirtualnaapteczka;

class Medicine {

    private String nameMedicine;
    private String kindOfMedicine;


    public Medicine(String nameMedicine, String kindOfMedicine) {
        this.nameMedicine = nameMedicine;
        this.kindOfMedicine = kindOfMedicine;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public String getKindOfMedicine() {
        return kindOfMedicine;
    }
}
