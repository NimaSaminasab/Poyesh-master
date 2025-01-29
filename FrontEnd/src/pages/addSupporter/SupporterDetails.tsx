import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { SupporterService } from '../../service/supporter.service';
import { toast } from "react-toastify";
import './SupporterDetails.scss';

const SupporterDetails: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [supporter, setSupporter] = useState<any>(null);
    

    useEffect(() => {
        if (id) {
            SupporterService.searchSupporter(id) // Pass `id` directly
                .then(res => {
                    setSupporter(res.data);
                })
                .catch(err => {
                    toast.error(`Failed to fetch supporter: ${err.message}`);
                });
        } else {
            toast.error("Supporter ID is missing");
        }
    }, [id]);

    if (!supporter) {
        return <div>Loading...</div>;
    }

    return (
        <div className="SupporterDetails">
            <h1>Detaljer for {supporter.fornavn || "N/A"} {supporter.etternavn || "N/A"}</h1>
            <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Telefon</th>
                            <th>Adresse</th>
                            <th>Postnummer</th>
                            <th>Poststed</th>
                            <th>Aktiv</th>
                        </tr>
                    </thead>
                    <tbody>
                    <tr>
    <td>{supporter.id || "N/A"}</td>
    <td>{supporter.telefon || "N/A"}</td>
    <td>{supporter.adresse || "N/A"}</td>
    <td>{supporter.postnummer || "N/A"}</td>
    <td>{supporter.poststed || "N/A"}</td>
    <td>{supporter.aktiv ? "Ja" : "Nei"}</td>
</tr>
                    </tbody>

                
                </table>
            
            <h2>Betalinger</h2>
            {supporter.betalingList && supporter.betalingList.length > 0 ? (
                
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Faktura nr.</th>
                            <th>Dato</th>
                            <th>Beløp</th>
                            <th>Student Name</th>
                        </tr>
                    </thead>
                    <tbody>
                        {supporter.betalingList.map((payment: any) => (
                            <tr key={payment.id}>
                                <td>{payment.id}</td>
                                <td>{payment.fakturaNummer}</td>
                                <td>{payment.dato}</td>
                                <td>{payment.belop}</td>
                                <td>{payment.elevName || "N/A"}</td> {/* Access student name */}
                                
                            </tr>
                        ))}
                    </tbody>
                </table>
                
            ) : (
                <p>No payments available</p>
            )}
        </div>

        
    );
};

export default SupporterDetails;
