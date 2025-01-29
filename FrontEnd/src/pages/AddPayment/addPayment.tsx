import React, { useState } from 'react';
import { Button, Paper, TextField } from "@mui/material";
import './addPayment.scss';
import DatePicker from "@hassanmojab/react-modern-calendar-datepicker";
import { useForm } from "../../hooks/useForm";
import { PaymentModel } from '../../model/payment.model';
import { PaymentService } from '../../service/payment.service';
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import moment from 'moment';

const AddPayment = () => {
    const navigate = useNavigate();

    // State for the date picker
    const [fDate, setFDate] = useState({
        year: moment().year(),
        month: moment().month() + 1,
        day: moment().date()
    });

    // Initialize form with default values
    const { registerControl, form, setField } = useForm<PaymentModel>({
        initialForm: {
            fakturaNummer: '',
            belop: '',
            dato: '',
            supporter: null, // Default to null
            elev: null,      // Default to null
        }
    });

    // Convert the date to the correct format for submission
    const newForm = {
        ...form,
        dato: moment(`${fDate.year}-${fDate.month}-${fDate.day}`, "YYYY-MM-DD").format("YYYY-MM-DD"),
    };

    // Handle form submission
    const handleAdd = () => {
        const paymentData: PaymentModel = {
            ...newForm,
        };

        // Logging for debugging purposes
        console.log("Payment data being sent:", paymentData);

        // Call the service to create a payment
        PaymentService.createPayment(paymentData)
            .then(() => {
                toast.success("Payment successfully created!");
                navigate(-1); // Navigate back to the previous page
            })
            .catch(err => {
                console.error("Error creating payment:", err.response?.data || err.message);
                toast.error("Failed to create payment.");
            });
    };

    return (
        <div className="addPayment">
            <h1>Add Payment</h1>
            <Paper className="addPayment__form">
                <TextField 
                    {...registerControl("fakturaNummer")} 
                    label="Faktura Nummer" 
                    required
                />
                <TextField 
                    {...registerControl("belop")} 
                    label="Beløp" 
                    type="number" 
                    required
                />
                <div>
                    <label>Date</label>
                    <DatePicker
                        value={fDate}
                        onChange={value => value && setFDate(value)}
                        locale="en"
                    />
                </div>
                <TextField 
                    label="Betalt av (Supporter ID)" 
                    type="number"
                    value={form.supporter || ''} // Display blank if null
                    onChange={(e) => {
                        const value = e.target.value;
                        setField("supporter", value === '' ? null : Number(value));
                    }}
                    required
                />
                <TextField 
                    label="Betalt til (Elev ID)" 
                    type="number"
                    value={form.elev || ''} // Display blank if null
                    onChange={(e) => {
                        const value = e.target.value;
                        setField("elev", value === '' ? null : Number(value));
                    }}
                    required
                />
                <Button
                    className="addPayment__button"
                    onClick={handleAdd}
                    variant="contained"
                    color="primary"
                >
                    Create
                </Button>
            </Paper>
        </div>
    );
};

export default AddPayment;
