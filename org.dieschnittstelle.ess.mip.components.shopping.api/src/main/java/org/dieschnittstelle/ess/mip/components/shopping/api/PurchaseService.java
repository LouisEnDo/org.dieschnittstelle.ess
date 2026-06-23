package org.dieschnittstelle.ess.mip.components.shopping.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

// TODO: PAT1: this is the interface to be provided as a rest service if rest service access is used
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/purchase")
public interface PurchaseService {

	public static class PurchaseDTO {
		public long shoppingCartId;
		public long touchpointId;
		public long customerId;

		public PurchaseDTO(long shoppingCartId, long touchpointId, long customerId) {
			this.shoppingCartId = shoppingCartId;
			this.touchpointId = touchpointId;
			this.customerId = customerId;
		}

		public PurchaseDTO(){}
	}

	@POST
	void purchaseCartAtTouchpointForCustomer(PurchaseDTO dto) throws ShoppingException;

}
