package org.dieschnittstelle.ess.mip.components.erp.crud.impl;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;
import org.dieschnittstelle.ess.entities.erp.Campaign;
import org.dieschnittstelle.ess.mip.components.erp.crud.api.ProductCRUD;

import java.util.List;

@ApplicationScoped
@Transactional
@Alternative
@Priority(Interceptor.Priority.APPLICATION+10)
public class ProductCRUDImpl implements ProductCRUD {

    @Inject
    @EntityManagerProvider.ERPDataAccessor
    private EntityManager em;

    @Override
    public AbstractProduct createProduct(AbstractProduct prod) {
        em.persist(prod);
        return prod;
    }

    @Override
    public List<AbstractProduct> readAllProducts() {
        Query query = em.createQuery("SELECT p FROM AbstractProduct p");
        return query.getResultList();
    }

    @Override
    public AbstractProduct updateProduct(AbstractProduct update) {
        return em.merge(update);
    }

    @Override
    public AbstractProduct readProduct(long productID) {
        return em.find(AbstractProduct.class,productID);
    }

    @Override
    public boolean deleteProduct(long productID) {
        em.remove(this.readProduct(productID));
        return true;
    }

    @Override
    public List<Campaign> getCampaignsForProduct(long productID) {
        Query query = em.createQuery(
                        "SELECT c FROM Campaign c " +
                                "JOIN c.bundles b " +
                                "WHERE b.product.id = :productID", Campaign.class);
        query.setParameter("productID", productID);

        return query.getResultList();
    }
}
