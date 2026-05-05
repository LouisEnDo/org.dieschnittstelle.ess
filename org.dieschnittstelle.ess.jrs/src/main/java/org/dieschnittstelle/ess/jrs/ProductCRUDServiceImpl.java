package org.dieschnittstelle.ess.jrs;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import org.apache.logging.log4j.Logger;
import org.dieschnittstelle.ess.entities.GenericCRUDExecutor;
import org.dieschnittstelle.ess.entities.erp.AbstractProduct;

import java.util.List;

/*
 * TODO JRS2: implementieren Sie hier die im Interface deklarierten Methoden
 */

public class ProductCRUDServiceImpl implements IProductCRUDService {
	protected static Logger logger = org.apache.logging.log4j.LogManager.getLogger(TouchpointCRUDServiceImpl.class);

	private GenericCRUDExecutor<AbstractProduct> productCrud;

	public ProductCRUDServiceImpl(@Context ServletContext servletContext, @Context HttpServletRequest request) {
		logger.info("<constructor>: " + servletContext + "/" + request);
		// read out the dataAccessor
		this.productCrud = (GenericCRUDExecutor<AbstractProduct>) servletContext.getAttribute("productCRUD");

		logger.debug("read out the productCRUD from the servlet context: " + this.productCrud);
	}

	@Override
	public AbstractProduct createProduct(
			AbstractProduct prod) {
		return this.productCrud.createObject(prod);
	}

	@Override
	public List<AbstractProduct> readAllProducts() {
		return this.productCrud.readAllObjects();
	}

	@Override
	public AbstractProduct updateProduct(long id,
			AbstractProduct update) {
		logger.info("Updating product");
		update.setId(id);

		return this.productCrud.updateObject(update);
	}

	@Override
	public boolean deleteProduct(long id) {
		boolean deleted = this.productCrud.deleteObject(id);

		logger.debug("tried to delete product " + deleted);
		if (!deleted){
			throw new NotFoundException("product not deleted");
		}

		return true;
	}

	@Override
	public AbstractProduct readProduct(long id) {
		AbstractProduct product = this.productCrud.readObject(id);

		if (product == null){
			throw new NotFoundException("Product not found");
		}

		return product;
	}
	
}
