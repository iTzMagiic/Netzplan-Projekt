import java.util.ArrayList;
import java.util.List;

public class Process {

    private String name;
    private int duration;
    private int faz, fez, saz, sez, freierPuffer, gesamtPuffer;

    // Konstruktor
    public Process(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getFaz() {
        return faz;
    }

    public void setFaz(int faz) {
        this.faz = faz;
    }

    public int getFez() {
        return fez;
    }

    public void setFez(int fez) {
        this.fez = fez;
    }

    public int getSaz() {
        return saz;
    }

    public void setSaz(int saz) {
        this.saz = saz;
    }

    public int getSez() {
        return sez;
    }

    public void setSez(int sez) {
        this.sez = sez;
    }

    public int getFreierPuffer() {
        return freierPuffer;
    }

    public void setFreierPuffer(int freierPuffer) {
        this.freierPuffer = freierPuffer;
    }

    public int getGesamtPuffer() {
        return gesamtPuffer;
    }

    public void setGesamtPuffer(int gesamtPuffer) {
        this.gesamtPuffer = gesamtPuffer;
    }

    @Override
    public String toString() {
        return "name: " + name;
    }
}
