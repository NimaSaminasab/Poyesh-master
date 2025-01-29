import axios from "axios";
import { AccountModel } from "../model/Account.model";
export class AccountService {
    static getAllAccount() {
        return axios.get<AccountModel[]>("/findAllBankInfo")
    }

    static searchAccount(searchBody: Partial<AccountModel>) {
        let searchfor = "/findBankInfo/" + searchBody.search ;
        return axios.get<AccountModel[]>(searchfor)
    }

     static createAccount(elevid: number, body: AccountModel) {
        return axios.post<AccountModel>(`/createBankInfo/${elevid}`, body);
    }
    
    static deActive(id: number) {
        return axios.get<AccountModel>("/deactivateAccount/" + id)
    }

    static reActive(id: number) {
        return axios.get<AccountModel>("/reactivateAccount/" + id)
    }
}
