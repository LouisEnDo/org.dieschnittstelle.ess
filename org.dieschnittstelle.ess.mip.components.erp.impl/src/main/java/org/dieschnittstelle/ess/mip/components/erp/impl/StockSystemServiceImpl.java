package org.dieschnittstelle.ess.mip.components.erp.impl;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystemService;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.mip.components.erp.dto.StockItemDto;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import java.util.List;

@ApplicationScoped
@Transactional
@Logged
@Alternative
@Priority(Interceptor.Priority.APPLICATION+10)
public class StockSystemServiceImpl implements StockSystemService {

    @Inject
    private StockSystem stockSystem;

    @Inject
    private ProductCRUD productCRUD;

    @Override
    public void addToStock(StockItemDto stockItemDto) {
        IndividualisedProductItem prod = (IndividualisedProductItem) productCRUD.readProduct(stockItemDto.getProductId());
        stockSystem.addToStock(prod,stockItemDto.getPosId(),stockItemDto.getUnits());
    }

    @Override
    public void removeFromStock(StockItemDto stockItemDto) {
        var product = (IndividualisedProductItem) productCRUD.readProduct(stockItemDto.getProductId());

        stockSystem.removeFromStock(product,stockItemDto.getPosId(),stockItemDto.getUnits());
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        if (pointOfSaleId == 0) {
            return stockSystem.getAllProductsOnStock();
        }
        return stockSystem.getProductsOnStock(pointOfSaleId);
    }

    @Override
    public int getUnitsOnStock(long productId, long pointOfSaleId) {
        var product = (IndividualisedProductItem) productCRUD.readProduct(productId);

        if (pointOfSaleId == 0){
            return stockSystem.getTotalUnitsOnStock(product);
        }
        return stockSystem.getUnitsOnStock(product,pointOfSaleId);
    }

    @Override
    public List<Long> getPointsOfSale(long productId) {
        var product = (IndividualisedProductItem) productCRUD.readProduct(productId);

        return stockSystem.getPointsOfSale(product);
    }
}