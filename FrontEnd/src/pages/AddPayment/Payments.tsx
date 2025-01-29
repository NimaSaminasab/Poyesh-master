import React, { useEffect, useState } from 'react';
import { PaymentModel } from '../../model/payment.model';
import { PaymentService } from '../../service/payment.service';
import { toast } from "react-toastify";
import './payments.scss';
import { Link } from "react-router-dom";
import { Button, Collapse, IconButton, Paper, TextField } from "@mui/material";
import { Search } from "@mui/icons-material";
import { useForm } from "../../hooks/useForm";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import moment from 'moment';

const Payments = () => {
    const [payments, setPayments] = useState<PaymentModel[]>([]);
    const [searchBoxOpen, setSearchBoxOpen] = useState(false);
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);

    const { registerControl, form, setForm } = useForm<PaymentModel>({
        initialForm: {
            fakturaNummer: "",
            belop: "",
            dato: "",
            supporter: 0,
            elev: 0,
        },
    });

    useEffect(() => {
        PaymentService.getAllPayment()
            .then(res => {
                if (res.data.length > 0) {
                    setPayments(res.data);
                } else {
                    toast.info("No payments found.");
                }
            })
            .catch(err => {
                toast.error("Failed to fetch payments: " + (err.response?.data?.error || err.message));
            });
    }, []);

    const handleSearch = () => {
        if (!form.fakturaNummer && !selectedDate) {
            toast.error("Please enter a faktura number or select a date to search.");
            return;
        }

        const searchQuery = selectedDate
            ? moment(selectedDate).format("YYYY-MM-DD") // Format date for search
            : form.fakturaNummer;

        PaymentService.searchPayment(searchQuery)
            .then(res => {
                if (res.data.length > 0) {
                    setPayments(res.data);
                } else {
                    toast.info("No payments found.");
                    setPayments([]);
                }

                // Clear the search inputs after search
                setForm((prev) => ({ ...prev, fakturaNummer: "" }));
                setSelectedDate(null);
            })
            .catch(err => {
                toast.error("Search failed: " + (err.response?.data?.error || err.message));
            });
    };

    return (
        <div className="payments">
            <header>
                <h1>Payment List</h1>
                <IconButton onClick={() => setSearchBoxOpen(open => !open)}>
                    <Search />
                </IconButton>
                <Link to="/app/payments/add">
                    <Button variant="contained" color="primary">Create</Button>
                </Link>
            </header>
            <Collapse in={searchBoxOpen}>
                <Paper className="payments__search">
                    <TextField
                        {...registerControl("fakturaNummer")}
                        label="Searchword"
                        value={form.fakturaNummer}
                    />
                    <DatePicker
                        selected={selectedDate}
                        onChange={(date) => setSelectedDate(date)}
                        dateFormat="yyyy-MM-dd"
                        placeholderText="Select a date"
                    />
                    <Button onClick={handleSearch} variant="contained" color="primary">
                        Search
                    </Button>
                </Paper>
            </Collapse>
            <table className="payments__table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Faktura Nummer</th>
                        <th>Beløp</th>
                        <th>Dato</th>
                        <th>Supporter</th>
                        <th>Elev</th>
                    </tr>
                </thead>
                <tbody>
                    {payments.map(payment => (
                        <tr key={payment.id}>
                            <td>{payment.id}</td>
                            <td>{payment.fakturaNummer}</td>
                            <td>{payment.belop}</td>
                            <td>{moment(payment.dato).format("YYYY-MM-DD")}</td>
                            <td>{payment.supporterName || "Unknown"}</td>
                            <td>{payment.elevName || "Unknown"}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default Payments;
