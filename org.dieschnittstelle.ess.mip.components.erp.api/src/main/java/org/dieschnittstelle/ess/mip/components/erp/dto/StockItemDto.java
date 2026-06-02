package org.dieschnittstelle.ess.mip.components.erp.dto;

public class StockItemDto {
    private long productId;
    private long posId;
    private int units;

    public StockItemDto(long productId, long posId, int units) {
        this.productId = productId;
        this.posId = posId;
        this.units = units;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getPosId() {
        return posId;
    }

    public void setPosId(long posId) {
        this.posId = posId;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
