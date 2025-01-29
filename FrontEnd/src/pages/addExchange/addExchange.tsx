import React, { useState } from 'react';
import { Button, Paper, TextField } from "@mui/material";
import './addExchange.scss';
import DatePicker from "@hassanmojab/react-modern-calendar-datepicker";
import { useForm } from "../../hooks/useForm";
import { ExchangeModel } from '../../model/exchange.mode';
import { ExchangeService } from '../../service/exchange.service';
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import moment from 'moment';

const AddExchange = () => {
    const navigate = useNavigate();
    const [Date, setDate] = useState({
        year: moment().year(),
        month: moment().month() + 1,
        day: moment().date(),
    });

    // Add `initialForm` to initialize `useForm`
    const { registerControl, form } = useForm<ExchangeModel>({
        initialForm: {
            id: 0,
            date:"" ,
            nok:  0,
            toman: 0,
            kurs: 0,
            gebyr: 0,
        },
    });

    const handleAdd = () => {
        const newForm = { ...form, date: `${Date.year}-${Date.month}-${Date.day}` };
        console.log(newForm.date) ;
        ExchangeService.createExchange(newForm)
            .then(() => {
                toast.success("Added successfully");
                navigate(-1);
            })
            .catch(err => {
                toast.error(err.message);
            });
    };

    return (
        <div className="addExchange">
            <h1>Add Exchange</h1>
            <Paper className="addExchange__form">
                <DatePicker
                    value={Date}
                    onChange={(value) => {
                        if (value) setDate(value);
                    }}
                    locale="en"
                />
                <TextField {...registerControl("nok")} label="NOK" />
                <TextField {...registerControl("toman")} label="Toman" />
                <TextField {...registerControl("kurs")} label="Kurs" />
                <TextField {...registerControl("gebyr")} label="Gebyr" />
                <Button
                    className="addExchange__button"
                    onClick={handleAdd}
                    variant="contained"
                >
                    Create
                </Button>
            </Paper>
        </div>
    );
};

export default AddExchange;