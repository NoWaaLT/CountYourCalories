package edu.vvk_pit_21_i_nt.countyourcalories;

public class product {
    private float Angliavandeniai;
    private float Baltymai;
    private float Kilokalorijos;
    private String Produktas;
    private float Riebalai;

    public product() {
        Angliavandeniai = 0.01f;
        Riebalai = 0.01f;
        Baltymai = 0.01f;
        Kilokalorijos = 0.01f;
    }

    public float getAngliavandeniai() {
        return Angliavandeniai;
    }

    public void setAngliavandeniai(float Angliavandeniai) {
        this.Angliavandeniai = Angliavandeniai;
    }

    public float getBaltymai() {
        return Baltymai;
    }

    public void setProduktas(String Produktas) {
        this.Produktas = Produktas;
    }

    public void setBaltymai(float Baltymai) {
        this.Baltymai = Baltymai;
    }

    public float getKilokalorijos() {
        return Kilokalorijos;
    }

    public void setKilokalorijos(float Kilokalorijos) {
        this.Kilokalorijos = Kilokalorijos;
    }

    public String getProduktas() {
        return Produktas;
    }

    public float getRiebalai() {
        return Riebalai;
    }

    public void setRiebalai(float Riebalai) {
        this.Riebalai = Riebalai;
    }
}
