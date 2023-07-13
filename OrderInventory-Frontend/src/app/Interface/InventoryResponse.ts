export interface InventoryResponse {
  inventoryId: number;
  storeData: {
    storeId: number;
  };
  productData: {
    productId: number;
  };
  productInventory: number;
}

export interface InventoryDetailStoreIdDto {
  inventoryId: number;
  productData: {
    productName: string;
  };
  storeData: {
    storeName: string;
  };
  orderStatus: string;
}


export interface InventoryWithShipmentsDto {
  inventoryId: number;
  productData: {
    productName: string;
  };
  storeData: {
    storeName: string;
  };
  productInventory: number;
  shipmentData: {
    shipmentId: number;
    deliveryAddress: string;
    shipmentStatus: string;
  }[];
}


export interface InventoryByOrderId {
  inventoryId: number;
  productData: {
    productName: string;
  };
  storeData: {
    storeName: string;
  };
  customerData: {
    emailAddress: string;
  };
}


export interface ShipmentStatusCountDTO {
  shipmentStatus: string;
  count: number;
}


export interface InventoryDetails {
  productName: string;
  unitPrice: number;
  storeName: string;
  webAddress: string;
  shipmentStatus: string;
  totalAmount: number;
}




