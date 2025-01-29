import axios from "axios";
import { FamilyModel } from "../model/family.model";

export class FamilyService {
    static getAllFamily() {
        return axios.get<FamilyModel[]>("/findAllFamily")
    }

    static searchFamily(searchBody: Partial<FamilyModel>) {
         let searchfor = "/findFamily/" + searchBody.search;
        return axios.get<FamilyModel[]>(searchfor)
    }

    static createFamily(body: FamilyModel) {
        return axios.post<FamilyModel>("/createFamily", body)
    }
   
    static assignFamily(familyId: number, studentId: number) {
            const body = {
                familyId: familyId,
                studentId: studentId,
            };
            return axios.post("/assignElevToFamily/" + studentId + "/" + familyId );
            
        }

    static deActive(id: number) { 
        return axios.get<FamilyModel>("/deactivateFamily/" + id)
    }

    static reActive(id: number) {
        return axios.get<FamilyModel>("/reactivateFamily/" + id)
    }
}
