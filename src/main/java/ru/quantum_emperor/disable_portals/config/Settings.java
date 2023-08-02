package ru.quantum_emperor.disable_portals.config;

public class Settings {
    private static final Settings instance = new Settings();
    private boolean isDisabledNether = true;
    private boolean isDisableEnd = true;
    private boolean isDisableEden = true;
    private Settings() {

    }

    public static Settings getInstance() {
        return instance;
    }

    public boolean isDisabledNether() {
        return isDisabledNether;
    }

    public void setDisabledNether(boolean disabledNether) {
        isDisabledNether = disabledNether;
    }

    public boolean isDisableEnd() {
        return isDisableEnd;
    }

    public void setDisableEnd(boolean disableEnd) {
        isDisableEnd = disableEnd;
    }

    public boolean isDisableEden() {
        return isDisableEden;
    }

    public void setDisableEden(boolean disableEden) {
        isDisableEden = disableEden;
    }
}
