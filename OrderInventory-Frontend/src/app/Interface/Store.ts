export interface Store {
  storeId: number;
  storeName: string;
  webAddress: string;
  physicalAddress: string;
  latitude: number;
  longitude: number;
  logo: string;
  logoMimeType: string;
  logoFilename: string;
  logoCharset: string;
  logoLastUpdated: Date;
}

export interface StoreIdInventories{

  inventoryId:number;

  productId:number;

  productName:number;

  unitPrice:number;

  productDetails:number;

}