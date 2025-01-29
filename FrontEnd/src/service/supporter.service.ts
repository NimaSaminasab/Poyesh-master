import axios from "axios";
import {SupporterModel} from "../model/supporter.model";

export class SupporterService {
    static getAllSupporters() {
        return axios.get<SupporterModel[]>("/findAllSupporter")
    }

   /* static searchSupporter(searchBody: Partial<SupporterModel>) {
        let searchfor = "/findSupporter/" + searchBody.search
       return axios.get<SupporterModel[]>(searchfor)
    }*/
    static searchSupporter(search: string | number) {
        const searchfor = `/findSupporter/${search}`;
        return axios.get<SupporterModel>(searchfor); // Adjust return type to a single model
    }

    static createSupporter(body: SupporterModel) {
        return axios.post<SupporterModel>("/createSupporter", body)
    }

    static deActive(id: number) {
        return axios.get<SupporterModel>("/deactivateSupporter/" + id)
    }

    static reActive(id: number) {
        return axios.get<SupporterModel>("/reactivateSupporter/" + id)
        
    }                                    
}
