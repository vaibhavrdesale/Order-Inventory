export class StoreName {
    orderId: number;
    orderStatus: string;
    storeName: string;
    webAddress: string;


    constructor(orderId: number, orderStatus: string, storeName: string, webAddress: string) {
        this.orderId = orderId
        this.orderStatus = orderStatus
        this.storeName = storeName
        this.webAddress = webAddress
    }
}
