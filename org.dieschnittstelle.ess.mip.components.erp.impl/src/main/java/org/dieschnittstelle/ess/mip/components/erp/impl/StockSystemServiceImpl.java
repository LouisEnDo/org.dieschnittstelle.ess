package org.dieschnittstelle.ess.mip.components.erp.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystemService;
import org.dieschnittstelle.ess.mip.components.erp.dto.StockItemDto;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import java.util.List;

@ApplicationScoped
@Transactional
@Logged
public class StockSystemServiceImpl implements StockSystemService {

    @Override
    public void addToStock(StockItemDto stockItemDto) {

    }

    @Override
    public void removeFromStock(StockItemDto stockItemDto) {

    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        return List.of();
    }

    @Override
    public int getUnitsOnStock(long productId, long pointOfSaleId) {
        return 0;
    }

    @Override
    public List<Long> getPointsOfSale(long productId) {
        return List.of();
    }
}