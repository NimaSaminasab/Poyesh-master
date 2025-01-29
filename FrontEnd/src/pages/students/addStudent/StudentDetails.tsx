import React, { useEffect, useState } from 'react';
import { useParams } from "react-router-dom";
import { StudentService } from '../../../service/student.service';
import { toast } from "react-toastify";
import './studentDetails.scss';

const StudentDetails = () => {
    const { id } = useParams<{ id: string }>(); // Extract student ID from URL
    const [student, setStudent] = useState<any>(null);

    useEffect(() => {
        if (id) {
            StudentService.searchStudents(id)
                .then(res => {
                    setStudent(res.data);
                })
                .catch(err => {
                    toast.error(err.message);
                });
        } else {
            toast.error("Student ID is missing");
        }
    }, [id]);

    if (!student) {
        return <div>Loading...</div>;
    }

    return (
        <div className="studentDetails">
            <h1>Detaljer for {student.fornavn} {student.etternavn}</h1>
            
            <h2>Personal Information</h2>
            <p>ID: {student.id}</p>
            <p>Personnummer: {student.personnummer}</p>
            <p>By: {student.city}</p>
            <p>Skole: {student.skolenavn}</p>
            <p>Aktiv: {student.aktiv ? "Ja" : "Nei"}</p>
            
            <h2>Familie</h2>
            {student.family ? (
                <>
                    <p>Far: {student.family.farFornavn} {student.family.farEtternavn}</p>
                    <p>Mor: {student.family.morFornavn} {student.family.morEtternavn}</p>
                </>
            ) : (
                <p>Ingen familie registrert</p>
            )}
            
            <h2>Betalinger</h2>
            <h3></h3> 
            {student.betalingList && student.betalingList.length > 0 ? (
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Faktura nr.</th>
                            <th>Dato</th>
                            <th>Beløp</th>
                        </tr>
                    </thead>
                    <tbody>
                        {student.betalingList.map((payment: any) => (
                            <tr key={payment.id}>
                                <td>{payment.id}</td>
                                <td>{payment.fakturaNummer}</td>
                                <td>{payment.dato}</td>
                                <td>{payment.belop}</td>
                            </tr>
                          
                        ))}
                    </tbody>
                    <thead>
                        <tr>
                            <th>Sum motatt</th>
                            <td> </td>
                            <td> </td>
                            <th>{student.motattSumTilNa}</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                            <tr >
                               
                                
                            </tr>
                          
                       
                    </tbody>
                </table>
                
            ) : (
                <p>No payments available</p>
            )}

<h2>Bankkonto</h2>
{student.bankInfoList && student.bankInfoList.length > 0 ? (
    <table>
        <thead>
            <tr>
                <th>Bank</th>
                <th>Navn </th>
                <th>Konto nummer</th>
                <th>Sheba nummer</th>
                <th>Kort nummer</th>
            </tr>
        </thead>
        <tbody>
            {student.bankInfoList.map((account: any) => (
                <tr key={account.id}>
                    <td>{account.bankNavn}</td>
                    <td>{account.kontoHoldersNavn}</td>
                    <td>{account.kontoNummer}</td>
                    <td>{account.shebaNummer}</td>
                    <td>{account.kortNummer}</td>
                </tr>
            ))}
        </tbody>
    </table>
) : (
    <p>No account linked</p>
)}
        </div>
    );
};

export default StudentDetails;
