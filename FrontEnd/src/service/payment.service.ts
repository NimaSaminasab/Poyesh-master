import axios from "axios";
import { PaymentModel } from "../model/payment.model";

axios.defaults.baseURL = "http://localhost:8080"; // Ensure backend base URL is correct

export class PaymentService {
    static getAllPayment() {
        return axios.get<PaymentModel[]>("/findAllBetaling"); // Matches backend URL
    }

    static searchPayment(searchWord: string) {
        return axios.get<PaymentModel[]>(`/findBetaling/${searchWord}`);
    }
    
    

    static createPayment(payment: PaymentModel) {
        const paymentData = {
            fakturaNummer: payment.fakturaNummer,
            belop: payment.belop,
            dato: payment.dato,
            supporterId: payment.supporter, // Map ID
            elevId: payment.elev,          // Map ID
        };
    
        console.log("Creating payment:", paymentData);
    
        return axios.post(`/createBetaling`, paymentData)
            .then(response => {
                console.log("Payment created successfully!", response.data);
            })
            .catch(error => {
                console.error("Error creating payment:", error);
                throw error;
            });
    }
    
    
}
