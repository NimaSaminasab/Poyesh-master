export interface StudentModel {
    id: number;
    fornavn: string;
    etternavn: string;
    personnummer: string;
    telefon1: string;
    telefon2: string;
    telefon3: string;
    city: string;
    fDato: string;
    skolenavn: string;
    behovSumPrManed: number;
    motattSumTilNa: number;
    bilde: string;
    film: string;
    aktiv : boolean;
    search?: string ;
}
