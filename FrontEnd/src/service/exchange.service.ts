import axios from "axios";
import { ExchangeModel } from "../model/exchange.mode";
export class ExchangeService {
    static getAllExchange() {
        return axios.get<ExchangeModel[]>("/findAllCurrencyExchange")
    }

    static searchExchange(searchBody: Partial<ExchangeModel>) {
        let searchword  = "/findCurrencyExchange/" + searchBody.date ;
        console.log(searchword ) ;
        return axios.get<ExchangeModel[]>( searchword)
    }

    static createExchange(body: ExchangeModel) {
        return axios.post<ExchangeModel>("/createCurrencyExchange", body)
    }

    
}
