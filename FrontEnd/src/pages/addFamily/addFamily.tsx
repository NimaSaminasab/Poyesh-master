import React, {useState} from 'react';
import {Button, Paper, TextField} from "@mui/material";
import './addFamily.scss' ;
import {useForm} from "../../hooks/useForm";
import { FamilyModel } from '../../model/family.model';
import { FamilyService } from '../../service/family.service';
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

const AddFamily = () => {
    const navigate = useNavigate()

    
    
    const { registerControl, form } = useForm<FamilyModel>({
        initialForm: {
            id: 0 ,
            farFornavn: "" ,
            farEtternavn: "" ,
            morFornavn: "" ,
            morEtternavn: "" ,
            isAktiv: false ,
            sumMotatt : 0 ,
            sumMotattToman : 0  
        
            
        },
    });
    function handleAdd() {
        const newForm = {...form}
         FamilyService.createFamily(newForm).then(res => {
            toast.success("added successfully")
            navigate(-1)
        }).catch(err => {
            toast.error(err.message)
        })
    }


    return (
        <div className="addFamily">
            <h1>Add Family</h1>
            <Paper className="addFamily__form">
         
                <TextField {...registerControl("farFornavn")} label="farForNavn"/>
                <TextField {...registerControl("farEtternavn")} label="farEtternavn"/>
                <TextField {...registerControl("morFornavn")} label="morFornavn"/>
                <TextField {...registerControl("morEtternavn")} label="morEtternavn"/>
                
                <Button className="addFamily__button" onClick={handleAdd} variant="contained">Create</Button>
            </Paper>
        </div>
    );
};

export default AddFamily;
