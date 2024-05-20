import { Product } from "./product-model";

export class PurchaseProduct {
    idPurchase?: number;
    payingMethod?: string;

    productId?: number;
    traderId?: number;
    payingInfo?: string;
    product? : Product;
    sellerUsername? : string;

}