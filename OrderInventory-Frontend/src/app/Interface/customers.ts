export interface Customers {
  customerId: number;
  emailAddress: string;
  fullName: string;

}
export interface OrderDto {
  orderId: number;
  orderTms: string;
  customerId: number;
  orderStatus: string;
  storeId: number;
} 

export interface CountDto {
  shipmentStatus: string;
  countCustomer: number;
}

export interface Customersname {
  customerId: number;
  emailAddress: string;
  fullName: string;
}

export interface Shipment {
  shipmentId: number;
  storeId: number;
  customerId: number;
  deliveryAddress: string;
  shipmentStatus: string;
}
export interface OrderDto {
  orderIdone: number;
  orderTms: string;
  customerId: number;
  orderStatus: string;
  storeId: number;
}
