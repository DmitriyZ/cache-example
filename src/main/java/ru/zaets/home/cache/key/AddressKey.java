package ru.zaets.home.cache.key;


import java.util.Objects;

/**
 *
 * @author <a href="mailto:sofyenkov@omnicomm.ru">Александр Софьенков</a>
 */
public class AddressKey {

    private double longitude;
    private double latitude;
    private String locale;

    public AddressKey(double longitude, double latitude, String locale) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.locale = locale;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.locale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AddressKey other = (AddressKey) obj;
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.locale, other.locale)) {
            return false;
        }
        return true;
    }

}
