import React, {useState} from 'react';
import {Button, Paper, TextField} from "@mui/material";
import './assignFamily.scss' ;
import {useForm} from "../../hooks/useForm";
import { FamilyAssignModel } from '../../model/familyAssign.model';
import { FamilyService } from '../../service/family.service';
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";

const AssignFamily = () => {
    const navigate = useNavigate()

    
      const { registerControl, form } = useForm<FamilyAssignModel>({
        initialForm: {
            familyId: 0,
            studentId: 0
            
        },
    });

   
    function handleAssign() {
        console.log("handleAssign") ;
        const { familyId, studentId } = form; // Assuming these fields are available in your form state
        FamilyService.assignFamily(familyId, studentId)
            .then(res => {
                toast.success("assigned successfully");
                navigate(-1);
            })
            .catch(err => {
                toast.error(err.message);
            });
    }
   


    return (
        <div className="assignFamily">
            <h1>Assign Family</h1>
            <Paper className="assignFamily__form">
         
                <TextField {...registerControl("familyId")} label="Family id"/>
                <TextField {...registerControl("studentId")} label="Student id"/>
                
                <Button className="assignFamily__button" onClick={handleAssign} variant="contained">Assign</Button>
            </Paper>
        </div>
    );
};

export default AssignFamily;
