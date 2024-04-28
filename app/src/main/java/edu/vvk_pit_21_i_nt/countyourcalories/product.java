package edu.vvk_pit_21_i_nt.countyourcalories;

public class product {
    float Angliavandeniai;
    float Baltymai;
    float Kilokalorijos;
    String Produktas;
    float Riebalai;

    public product() {
        Angliavandeniai = 0.01F;
        Riebalai = 0.01F;
        Baltymai = 0.01F;
        Kilokalorijos = 0.01F;
    }

    public float getAngliavandeniai() {
        return Angliavandeniai;
    }

    public void setAngliavandeniai(Float angliavandeniai) {
        Angliavandeniai = angliavandeniai;
    }

    public float getBaltymai() {
        return Baltymai;
    }

    public void setProduktas(String produktas) {
        Produktas = produktas;
    }

    public void setBaltymai(Float baltymai) {
        Baltymai = baltymai;
    }

    public float getKilokalorijos() {
        return Kilokalorijos;
    }

    public void setKilokalorijos(Float kilokalorijos) {
        Kilokalorijos = kilokalorijos;
    }

    public String getProduktas() {
        return Produktas;
    }

    public float getRiebalai() {
        return Riebalai;
    }

    public void setRiebalai(Float riebalai) {
        Riebalai = riebalai;
    }


    // Covert product to string
    @Override
    public String toString() {
        return getProduktas();
    }
}


