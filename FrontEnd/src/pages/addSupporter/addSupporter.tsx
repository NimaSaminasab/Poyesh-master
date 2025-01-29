import React, {useState} from 'react';
import {Button, Paper, TextField} from "@mui/material";
import './addSupporter.scss'
import DatePicker, {DayValue} from "@hassanmojab/react-modern-calendar-datepicker";
import {useForm} from "../../hooks/useForm";
import {SupporterModel} from "../../model/supporter.model";
import {SupporterService} from "../../service/supporter.service";
import {toast} from "react-toastify";
import moment from 'moment-jalaali';
import {useNavigate} from "react-router-dom";

const AddSupporter = () => {
    const navigate = useNavigate()

   
    
    const { registerControl, form } = useForm<SupporterModel>({
        initialForm: {
            id : 0 ,
            fornavn : "" ,
            etternavn : "" ,
            telefon : "" ,
            adresse : "" ,
            postnummer : "" ,
            poststed : "" ,
            betaltTilNa : 0 ,
            betaltTilNaToman : 0 ,
            aktiv : true
        },
    });
    function handleAdd() {
        const newForm = {...form}
         SupporterService.createSupporter(newForm).then(res => {
            toast.success("added successfully")
            navigate(-1)
        }).catch(err => {
            toast.error(err.message)
        })
    }


    return (
        <div className="addSupporter">
            <h1>Add Supporter</h1>
            <Paper className="addSupporter__form">
                <TextField {...registerControl("fornavn")} label="fornavn"/>
                <TextField {...registerControl("etternavn")} label="etternavn"/>
                <TextField {...registerControl("telefon")} label="telefon"/>
                <TextField {...registerControl("adresse")} label="adresse"/>
                <TextField {...registerControl("postnummer")} label="postnummer"/>
                <TextField {...registerControl("poststed")} label="poststed"/>
               
               
                <Button className="addStudent__button" onClick={handleAdd} variant="contained">Create</Button>
            </Paper>
        </div>
    );
};

export default AddSupporter;
