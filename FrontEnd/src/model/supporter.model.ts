export interface SupporterModel {
   
    id : number ;
    fornavn : string ;
    etternavn : string ;
    telefon : string ;
    adresse : string ;
    postnummer : string ;
    poststed : string ;
    betaltTilNa : number ;
    betaltTilNaToman : number ;
    aktiv : boolean ;
    search?: string ;
}
