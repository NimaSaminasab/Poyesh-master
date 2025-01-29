export interface PaymentModel {
    id?: number; 
    fakturaNummer: string; 
    belop: string; 
    dato : string ;
    supporter: number | null ; 
    elev: number | null ;
    toman?: string; 
    supporterName?: string; // Add name fields
    elevName?: string; 
}
