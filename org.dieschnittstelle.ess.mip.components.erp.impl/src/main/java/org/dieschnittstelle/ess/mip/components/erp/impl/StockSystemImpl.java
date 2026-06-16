package org.dieschnittstelle.ess.mip.components.erp.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.IndividualisedProductItem;
import org.dieschnittstelle.ess.entities.erp.PointOfSale;
import org.dieschnittstelle.ess.entities.erp.StockItem;
import org.dieschnittstelle.ess.mip.components.erp.api.StockSystem;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.PointOfSaleCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;
import org.dieschnittstelle.ess.mip.components.erp.crud.impl.StockItemCRUD;
import org.dieschnittstelle.ess.utils.interceptors.Logged;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Long.sum;

@ApplicationScoped
@Transactional
@Logged
public class StockSystemImpl implements StockSystem {

    @Inject
    private PointOfSaleCRUD posCrud;

    @Inject
    private StockItemCRUD stockItemCRUD;


    @Override
    public void addToStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        PointOfSale pos = posCrud.readPointOfSale(pointOfSaleId);
        StockItem si = stockItemCRUD.readStockItem(product, pos);

        if (si == null){
            stockItemCRUD.createStockItem(new StockItem(product,pos,units));
            return;
        }

        int newUnits = si.getUnits() + units;

        if (newUnits < 0){
            newUnits = 0;
        }
        si.setUnits(newUnits);
        stockItemCRUD.updateStockItem(si);
    }

    @Override
    public void removeFromStock(IndividualisedProductItem product, long pointOfSaleId, int units) {
        addToStock(product,pointOfSaleId, -units);
    }

    @Override
    public List<IndividualisedProductItem> getProductsOnStock(long pointOfSaleId) {
        PointOfSale pos = posCrud.readPointOfSale(pointOfSaleId);

        var stockItems = stockItemCRUD.readStockItemsForPointOfSale(pos);

        return stockItems.stream().map(StockItem::getProduct).toList();
    }

    @Override
    public List<IndividualisedProductItem> getAllProductsOnStock() {
        var posList = posCrud.readAllPointsOfSale();

        Set<IndividualisedProductItem> products= new HashSet<>();
        for (PointOfSale pos : posList){
            var stockItems = stockItemCRUD.readStockItemsForPointOfSale(pos);
            stockItems.forEach(stockItem -> products.add(stockItem.getProduct()));
        }
        return products.stream().toList();
    }

    @Override
    public int getUnitsOnStock(IndividualisedProductItem product, long pointOfSaleId) {

        PointOfSale pos = posCrud.readPointOfSale(pointOfSaleId);
        StockItem si = stockItemCRUD.readStockItem(product, pos);

        return si.getUnits();
    }

    @Override
    public int getTotalUnitsOnStock(IndividualisedProductItem product) {
        List<StockItem> list =  stockItemCRUD.readStockItemsForProduct(product);
        return list.stream().mapToInt(StockItem::getUnits).sum();
    }

    @Override
    public List<Long> getPointsOfSale(IndividualisedProductItem product) {
        List<StockItem> list =  stockItemCRUD.readStockItemsForProduct(product);
        return list.stream().map(si -> si.getPos().getId()).toList();
    }
}
