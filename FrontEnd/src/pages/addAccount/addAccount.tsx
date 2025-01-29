import React, {useState} from 'react';
import {Button, Paper, TextField} from "@mui/material";
import './addAccount.scss' ;
import {useForm} from "../../hooks/useForm";
import { AccountModel } from '../../model/Account.model';
import { AccountService } from '../../service/account.service';
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

const AddAccount = () => {
    const navigate = useNavigate()

    
   

    const { registerControl, form } = useForm<AccountModel>({
        initialForm: {
            id : 0 ,
            bankNavn  : "", 
            kontoHoldersNavn : "",
            kontoNummer : "", 
            shebaNummer : "",
            kortNummer : "" ,
            
        },
    });

    function handleAdd() {
        const newForm = { ...form };
        const { elevId, ...accountData } = newForm;
    
        if (elevId) {
            AccountService.createAccount(elevId, accountData)
                .then(res => {
                    toast.success("Added successfully");
                    navigate(-1);
                })
                .catch(err => {
                    toast.error(err.message);
                });
        } else {
            toast.error("Please provide elevId");
        }
    }


    return (
        <div className="addAccount">
            <h1>Add Account</h1>
            <Paper className="addAccount__form">
         
                <TextField {...registerControl("elevId")} label="elevId"/>
                <TextField {...registerControl("bankNavn")} label="bankNavn"/>
                <TextField {...registerControl("kontoHoldersNavn")} label="kontoHoldersNavn"/>
                <TextField {...registerControl("kontoNummer")} label="kontoNummer"/>
                <TextField {...registerControl("shebaNummer")} label="shebaNummer"/>
                <TextField {...registerControl("kortNummer")} label="kortNummer"/>
  
                <Button className="addAccount__button" onClick={handleAdd} variant="contained">Create</Button>
            </Paper>
        </div>
    );
};

export default AddAccount;
