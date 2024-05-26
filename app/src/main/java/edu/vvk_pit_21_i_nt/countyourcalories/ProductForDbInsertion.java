package edu.vvk_pit_21_i_nt.countyourcalories;

import com.google.firebase.database.PropertyName;

public class ProductForDbInsertion {
    float Angliavandeniai;
    float Baltymai;
    float Kilokalorijos;
    String Produktas;
    float Riebalai;

    public ProductForDbInsertion () {
        Angliavandeniai = 0.01F;
        Riebalai = 0.01F;
        Baltymai = 0.01F;
        Kilokalorijos = 0.01F;
    }

    @PropertyName("Angliavandeniai")
    public float getAngliavandeniai() {
        return Angliavandeniai;
    }

    @PropertyName("Angliavandeniai")
    public void setAngliavandeniai(Float angliavandeniai) {
        Angliavandeniai = angliavandeniai;
    }

    @PropertyName("Baltymai")
    public float getBaltymai() {
        return Baltymai;
    }

    @PropertyName("Baltymai")
    public void setBaltymai(Float baltymai) {
        Baltymai = baltymai;
    }

    @PropertyName("Kilokalorijos")
    public float getKilokalorijos() {
        return Kilokalorijos;
    }

    @PropertyName("Kilokalorijos")
    public void setKilokalorijos(Float kilokalorijos) {
        Kilokalorijos = kilokalorijos;
    }

    @PropertyName("Produktas")
    public String getProduktas() {
        return Produktas;
    }

    @PropertyName("Produktas")
    public void setProduktas(String produktas) {
        Produktas = produktas;
    }

    @PropertyName("Riebalai")
    public float getRiebalai() {
        return Riebalai;
    }

    @PropertyName("Riebalai")
    public void setRiebalai(Float riebalai) {
        Riebalai = riebalai;
    }

    // Covert product to string
    @Override
    public String toString() {
        return getProduktas();
    }
}