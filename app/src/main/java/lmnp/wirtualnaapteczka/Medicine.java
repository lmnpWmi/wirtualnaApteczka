package lmnp.wirtualnaapteczka;

import java.io.Serializable;

class Medicine implements Serializable {

    private String name;
    private String kind;


    public Medicine(String name, String kind) {
        this.name = name;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public String getKind() {
        return kind;
    }
}
