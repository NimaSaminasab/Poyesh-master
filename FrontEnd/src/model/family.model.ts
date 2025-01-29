export interface FamilyModel {
    id: number;
    farFornavn: string;
    farEtternavn: string;
    morFornavn: string;
    morEtternavn: string;
    isAktiv: boolean;
    sumMotatt : number ;
    sumMotattToman : number 
    search?: string ;

}
