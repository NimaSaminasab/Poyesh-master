import React, {useState} from 'react';
import {Button, Paper, TextField} from "@mui/material";
import './addStudent.scss'
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../../hooks/useForm";
import {StudentModel} from "../../../model/student.model";
import {StudentService} from "../../../service/student.service";
import {toast} from "react-toastify";
import moment from 'moment-jalaali';
import {useNavigate} from "react-router-dom";

const AddStudent = () => {
    const navigate = useNavigate()

    const [fDate, setFDate] = useState<DayValue>({
        year: moment().jYear(),
        month: moment().jMonth(),
        day: moment().jDate()
    });
    const { registerControl, form } = useForm<StudentModel>({
        initialForm: {
            id: 0,
            fornavn: "" ,
            etternavn: "" ,
            personnummer: "" ,
            telefon1: "" ,
            telefon2: "" ,
            telefon3: "" ,
            city: "" ,
            fDato: "" ,
            skolenavn: "" ,
            behovSumPrManed: 0 ,
            motattSumTilNa: 0 ,
            bilde: "" ,
            film: "" ,
            aktiv : true 
        },
    });

    function handleAdd() {
        const newForm = {...form}
        newForm.fDato = moment(`${fDate?.year}-${fDate?.month}-${fDate?.day}`, "jYYYY-jM-jD").toISOString()
        StudentService.createStudent(newForm).then(res => {
            toast.success("added successfully")
            navigate(-1)
        }).catch(err => {
            toast.error(err.message)
        })
    }


    return (
        <div className="addStudent">
            <h1>Add Student</h1>
            <Paper className="addStudent__form">
                <TextField {...registerControl("fornavn")} label="fornavn"/>
                <TextField {...registerControl("etternavn")} label="etternavn"/>
                <TextField {...registerControl("personnummer")} label="personnummer"/>
                <TextField {...registerControl("telefon1")} label="telefon1"/>
                <TextField {...registerControl("telefon2")} label="telefon2"/>
                <TextField {...registerControl("telefon3")} label="telefon3"/>
                <TextField {...registerControl("city")} label="city"/>
                <div>FDate
                    <DatePicker locale="fa" value={fDate} onChange={value => setFDate(value)}/>
                </div>
                <TextField {...registerControl("skolenavn")} label="skolenavn"/>
                <TextField {...registerControl("behovSumPrManed")} label="behovSumPrManed"/>
                <TextField {...registerControl("bilde")} label="bilde"/>
                <TextField {...registerControl("film")} label="film"/>
                <Button className="addStudent__button" onClick={handleAdd} variant="contained">Create</Button>
            </Paper>
        </div>
    );
};

export default AddStudent;
