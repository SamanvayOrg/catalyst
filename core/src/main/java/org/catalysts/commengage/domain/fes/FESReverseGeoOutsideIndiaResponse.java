package org.catalysts.commengage.domain.fes;

public class FESReverseGeoOutsideIndiaResponse implements FESReverseGeoResponse {
    @Override
    public String getCountry() {
        return "Unknown";
    }

    @Override
    public String getState() {
        return null;
    }

    @Override
    public String getDistrict() {
        return null;
    }

    @Override
    public String getSubDistrict() {
        return null;
    }

    @Override
    public String getBlock() {
        return null;
    }

    @Override
    public String getVillageCity() {
        return null;
    }

    @Override
    public String getPanchayat() {
        return null;
    }
}
