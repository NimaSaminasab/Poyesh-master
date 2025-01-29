import { StudentModel } from "./student.model";

export interface AccountModel {
    id: number;
    bankNavn: string;
    kontoHoldersNavn: string;
    kontoNummer: string;
    shebaNummer: string;
    kortNummer: string;
    elevId?: number | null; // Adding elevId explicitly here for easier access
    elev?: StudentModel; // Use StudentModel instead of Elev
    elevFullName?: string; // Add this for mapping the full name
    search?: string;
}
